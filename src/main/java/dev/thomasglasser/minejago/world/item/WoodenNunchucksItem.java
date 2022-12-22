package dev.thomasglasser.minejago.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.thomasglasser.minejago.client.renderer.item.WoodenNunchucksRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class WoodenNunchucksItem extends Item implements GeoItem
{
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private Multimap<Attribute, AttributeModifier> defaultModifiers;

    public WoodenNunchucksItem(Properties pProperties) {
        super(pProperties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "use_controller", state -> PlayState.CONTINUE)
                .triggerableAnim("charge", DefaultAnimations.ATTACK_CHARGE)
                .triggerableAnim("swing", DefaultAnimations.ATTACK_SWING));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();

        if (!tag.contains("AttackDamage"))
            tag.putInt("AttackDamage", 1);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", tag.getInt("AttackDamage"), AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();

        return slot == EquipmentSlot.MAINHAND ? defaultModifiers: super.getAttributeModifiers(slot, stack);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return stack.getOrCreateTag().getInt("AttackDamage");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level instanceof ServerLevel serverLevel)
            triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "use_controller", "charge");
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 100;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (count <= 1)
        {
            player.releaseUsingItem();
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLevel instanceof ServerLevel serverLevel)
            triggerAnim(pLivingEntity, GeoItem.getOrAssignId(pStack, serverLevel), "use_controller", "swing");

        pStack.getOrCreateTag().putInt("AttackDamage", ((getUseDuration(pStack) - pTimeCharged) / 10));

        ((Player)pLivingEntity).getCooldowns().addCooldown(this, 60);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private WoodenNunchucksRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new WoodenNunchucksRenderer();

                return this.renderer;
            }
        });
    }

    public static void resetDamage(ItemStack stack)
    {
        stack.getOrCreateTag().putInt("AttackDamage", 0);
    }
}
