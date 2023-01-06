package dev.thomasglasser.minejago.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Supplier;

public class WoodenNunchucksItem extends Item implements IModeledItem, IFabricGeoItem
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private MinejagoBlockEntityWithoutLevelRenderer bewlr;

    private Multimap<Attribute, AttributeModifier> defaultModifiers;

    public WoodenNunchucksItem(Properties pProperties) {
        super(pProperties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "use_controller", state -> PlayState.CONTINUE)
                .triggerableAnim("charge", DefaultAnimations.ATTACK_CHARGE)
                .triggerableAnim("swing", DefaultAnimations.ATTACK_SWING)
                .triggerableAnim("idle", DefaultAnimations.IDLE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private Multimap<Attribute, AttributeModifier> getAttributeModifiersInternal(EquipmentSlot slot, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();

        if (!tag.contains("AttackDamage"))
            tag.putInt("AttackDamage", 1);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (slot == EquipmentSlot.MAINHAND)
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", tag.getInt("AttackDamage"), AttributeModifier.Operation.ADDITION));

        return builder.build();
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
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration <= 1)
        {
            livingEntity.releaseUsingItem();
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
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player && !player.isUsingItem() && !player.getCooldowns().isOnCooldown(this))
        {
            if (pLevel instanceof ServerLevel)
                triggerAnim(pEntity, GeoItem.getOrAssignId(pStack, (ServerLevel) pLevel), "use_controller", "idle");
            resetDamage(pStack);
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public static void resetDamage(ItemStack stack)
    {
        stack.getOrCreateTag().putInt("AttackDamage", 0);
    }

    @Override
    public MinejagoBlockEntityWithoutLevelRenderer getBEWLR() {
        if (bewlr == null)
            bewlr = new MinejagoBlockEntityWithoutLevelRenderer();
        return bewlr;
    }
}
