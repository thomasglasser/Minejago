package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownBoneKnife;
import dev.thomasglasser.tommylib.api.world.item.ThrowableSwordItem;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class BoneKnifeItem extends ThrowableSwordItem {
    public BoneKnifeItem(Tier pTier, Properties pProperties) {
        super(pTier, pProperties);
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                if (!pLevel.isClientSide) {
                    pStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(pEntityLiving.getUsedItemHand()));
                    ThrownBoneKnife thrownBoneKnife = new ThrownBoneKnife(pLevel, player, pStack);
                    thrownBoneKnife.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                    if (player.getAbilities().instabuild) {
                        thrownBoneKnife.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    pLevel.addFreshEntity(thrownBoneKnife);
                    pLevel.playSound(null, thrownBoneKnife, MinejagoSoundEvents.BONE_KNIFE_THROW.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    if (!player.getAbilities().instabuild) {
                        player.getInventory().removeItem(pStack);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }
}
