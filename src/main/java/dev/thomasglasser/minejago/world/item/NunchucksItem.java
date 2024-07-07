package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.item.NunchucksRenderer;
import dev.thomasglasser.tommylib.api.world.item.BaseModeledItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NunchucksItem extends BaseModeledItem implements GeoItem {
    public static final ResourceLocation DAMAGE_MODIFIER = Minejago.modLoc("nunchuck_swinging");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private BlockEntityWithoutLevelRenderer bewlr;

    public NunchucksItem(Properties pProperties) {
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level instanceof ServerLevel serverLevel)
            triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(hand), serverLevel), "use_controller", "charge");
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity p_344979_) {
        return 100;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration <= 1) {
            livingEntity.releaseUsingItem();
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLevel instanceof ServerLevel serverLevel)
            triggerAnim(pLivingEntity, GeoItem.getOrAssignId(pStack, serverLevel), "use_controller", "swing");

        ItemAttributeModifiers original = pStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        ItemAttributeModifiers.Builder itemAttributeModifiers = ItemAttributeModifiers.builder();
        if (original != null) original.modifiers().forEach(entry -> {
            if (!entry.modifier().id().equals(DAMAGE_MODIFIER))
                itemAttributeModifiers.add(entry.attribute(), entry.modifier(), entry.slot());
        });
        itemAttributeModifiers.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER, ((getUseDuration(pStack, pLivingEntity) - pTimeCharged) / 10.0), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        pStack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers.build());

        ((Player) pLivingEntity).getCooldowns().addCooldown(this, 100);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player && !player.isUsingItem() && !player.getCooldowns().isOnCooldown(this)) {
            if (pLevel instanceof ServerLevel serverLevel)
                triggerAnim(pEntity, GeoItem.getOrAssignId(pStack, serverLevel), "use_controller", "idle");
            resetDamage(pStack);
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public static void resetDamage(ItemStack stack) {
        ItemAttributeModifiers original = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (original != null) {
            ItemAttributeModifiers.Builder itemAttributeModifiers = ItemAttributeModifiers.builder();
            original.modifiers().forEach(entry -> {
                if (!entry.modifier().id().equals(DAMAGE_MODIFIER))
                    itemAttributeModifiers.add(entry.attribute(), entry.modifier(), entry.slot());
            });
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers.build());
        }
    }

    @Override
    public BlockEntityWithoutLevelRenderer getBEWLR() {
        if (bewlr == null) bewlr = new NunchucksRenderer(BuiltInRegistries.ITEM.getKey(this));
        return bewlr;
    }
}
