package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownBambooStaff;
import dev.thomasglasser.tommylib.api.world.item.ModeledItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class BambooStaffItem extends ThrowableSwordItem implements ModeledItem
{
    private BlockEntityWithoutLevelRenderer bewlr;

    public BambooStaffItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                if (!pLevel.isClientSide) {
                    pStack.hurtAndBreak(1, player, (p_43388_) -> {
                        p_43388_.broadcastBreakEvent(pEntityLiving.getUsedItemHand());
                    });
                    ThrownBambooStaff thrownBambooStaff = new ThrownBambooStaff(pLevel, player, pStack);
                    thrownBambooStaff.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                    if (player.getAbilities().instabuild) {
                        thrownBambooStaff.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    pLevel.addFreshEntity(thrownBambooStaff);
                    pLevel.playSound(null, thrownBambooStaff, MinejagoSoundEvents.BAMBOO_STAFF_THROW.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!player.getAbilities().instabuild) {
                        player.getInventory().removeItem(pStack);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public BlockEntityWithoutLevelRenderer getBEWLR() {
        if (bewlr == null) bewlr = new MinejagoBlockEntityWithoutLevelRenderer();
        return bewlr;
    }
}
