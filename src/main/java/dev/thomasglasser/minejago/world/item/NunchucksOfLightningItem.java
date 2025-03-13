package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.item.NunchucksOfLightningRenderer;
import dev.thomasglasser.tommylib.api.client.renderer.BewlrProvider;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NunchucksOfLightningItem extends GoldenWeaponItem implements GeoItem {
    public static final ResourceLocation DAMAGE_MODIFIER = Minejago.modLoc("nunchuck_swinging");
    public static final String USE_CONTROLLER = "use_controller";
    public static final String CHARGE = "charge";
    public static final String SWING = "swing";

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public NunchucksOfLightningItem(Properties pProperties) {
        super(pProperties);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, USE_CONTROLLER, state -> {
            if (state.getController().getCurrentRawAnimation() == null || state.isCurrentAnimation(DefaultAnimations.IDLE))
                return state.setAndContinue(DefaultAnimations.IDLE);
            return PlayState.STOP;
        })
                .triggerableAnim(CHARGE, DefaultAnimations.ATTACK_CHARGE)
                .triggerableAnim(SWING, DefaultAnimations.ATTACK_SWING));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected InteractionResultHolder<ItemStack> doUse(Level level, Player player, InteractionHand usedHand) {
        if (player.getItemInHand(usedHand).has(DataComponents.DAMAGE)) {
            if (player instanceof ServerPlayer serverPlayer) {
                HitResult hitresult = ProjectileUtil.getHitResultOnViewVector(player, entity -> entity.isAlive() && entity.isPickable(), 100);
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt.setCause(serverPlayer);
                if (hitresult instanceof EntityHitResult entityHitResult) {
                    lightningBolt.setPos(entityHitResult.getEntity().position());
                } else if (hitresult instanceof BlockHitResult blockHitResult) {
                    BlockPos pos = blockHitResult.getBlockPos();
                    while (level.getBlockState(pos).isAir())
                        pos = pos.below();
                    lightningBolt.setPos(pos.above().getBottomCenter());
                } else {
                    lightningBolt.setPos(hitresult.getLocation());
                }
                level.addFreshEntity(lightningBolt);
                player.getCooldowns().addCooldown(this, 10);
            }
        } else {
            if (level instanceof ServerLevel serverLevel)
                triggerAnim(player, GeoItem.getOrAssignId(player.getItemInHand(usedHand), serverLevel), USE_CONTROLLER, CHARGE);
            player.startUsingItem(usedHand);
        }
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    protected InteractionResult doUseOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && context.getItemInHand().has(DataComponents.DAMAGE)) {
            if (player instanceof ServerPlayer serverPlayer) {
                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(context.getLevel());
                if (lightningBolt != null) {
                    lightningBolt.setPos(context.getClickLocation());
                    lightningBolt.setCause(serverPlayer);
                    context.getLevel().addFreshEntity(lightningBolt);
                }
            }
            player.getCooldowns().addCooldown(this, 10);
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity p_344979_) {
        return 100;
    }

    @Override
    protected void doOnUsingTick(ItemStack stack, LivingEntity player, int remainingUseDuration) {
        if (remainingUseDuration <= 1) {
            player.releaseUsingItem();
        }
    }

    @Override
    protected void doReleaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (level instanceof ServerLevel serverLevel)
            triggerAnim(livingEntity, GeoItem.getOrAssignId(stack, serverLevel), USE_CONTROLLER, SWING);

        ItemAttributeModifiers.Builder itemAttributeModifiers = resetDamage(stack);
        itemAttributeModifiers.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER, ((getUseDuration(stack, livingEntity) - timeCharged) / 10.0), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers.build());
        stack.set(DataComponents.MAX_DAMAGE, 100);
        stack.set(DataComponents.DAMAGE, 0);
        if (livingEntity instanceof Player player)
            player.getCooldowns().addCooldown(this, 10);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        Integer damage = pStack.get(DataComponents.DAMAGE);
        Integer maxDamage = pStack.get(DataComponents.MAX_DAMAGE);
        if (pEntity instanceof Player player && !player.isUsingItem() && maxDamage != null && damage != null) {
            damage += 1;
            if (damage >= maxDamage) {
                if (pLevel instanceof ServerLevel serverLevel)
                    stopTriggeredAnim(pEntity, GeoItem.getOrAssignId(pStack, serverLevel), USE_CONTROLLER, null);
                pStack.set(DataComponents.ATTRIBUTE_MODIFIERS, resetDamage(pStack).build());
                pStack.remove(DataComponents.DAMAGE);
                pStack.remove(DataComponents.MAX_DAMAGE);
                player.getCooldowns().addCooldown(this, 10);
            } else
                pStack.set(DataComponents.DAMAGE, damage);
        }
    }

    public static ItemAttributeModifiers.Builder resetDamage(ItemStack stack) {
        ItemAttributeModifiers original = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if (original != null) {
            ItemAttributeModifiers.Builder itemAttributeModifiers = ItemAttributeModifiers.builder();
            original.modifiers().forEach(entry -> {
                if (!entry.modifier().id().equals(DAMAGE_MODIFIER))
                    itemAttributeModifiers.add(entry.attribute(), entry.modifier(), entry.slot());
            });
            return itemAttributeModifiers;
        }
        return ItemAttributeModifiers.builder();
    }

    @Override
    public void createBewlrProvider(Consumer<BewlrProvider> consumer) {
        consumer.accept(new BewlrProvider() {
            private NunchucksOfLightningRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getBewlr() {
                if (renderer == null) renderer = new NunchucksOfLightningRenderer();
                return renderer;
            }
        });
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.get(DataComponents.MAX_DAMAGE) != null;
    }
}
