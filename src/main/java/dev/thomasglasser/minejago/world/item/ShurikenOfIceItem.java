package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.tags.MinejagoPowerTags;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownShurikenOfIce;
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ShurikenOfIceItem extends GoldenWeaponItem implements ProjectileItem {
    public ShurikenOfIceItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canPowerHandle(ResourceKey<Power> power, Level level) {
        return level.holder(power).orElseThrow().is(MinejagoPowerTags.CAN_USE_SHURIKEN_OF_ICE);
    }

    @Override
    protected InteractionResultHolder<ItemStack> doUse(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!level.isClientSide) {
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(usedHand));
            ThrownShurikenOfIce thrown = new ThrownShurikenOfIce(level, player, stack, player.isShiftKeyDown());
            thrown.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            if (player.hasInfiniteMaterials()) {
                thrown.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            level.addFreshEntity(thrown);
            level.playSound(null, thrown, MinejagoSoundEvents.SHURIKEN_OF_ICE_THROW.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            ItemUtils.safeShrink(1, stack, player);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    @Override
    protected InteractionResult doUseOn(UseOnContext context) {
        if (context.getPlayer() != null)
            return doUse(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
        return InteractionResult.PASS;
    }

    @Override
    protected void doReleaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {}

    @Override
    protected void doOnUsingTick(ItemStack stack, LivingEntity player, int count) {}

    @Override
    protected void goCrazy(Player player) {
        player.addEffect(new MobEffectInstance(MinejagoMobEffects.FROZEN.asReferenceFrom(player.level().registryAccess()), -1, 0));
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        return new ThrownShurikenOfIce(level, position.x(), position.y(), position.z(), itemStack, false);
    }
}
