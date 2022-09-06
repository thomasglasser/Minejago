package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.world.entity.projectile.ThrownBoneKnife;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class BoneKnifeItem extends ThrowableSwordItem
{
    public BoneKnifeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
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
                        ThrownBoneKnife thrownBoneKnife = new ThrownBoneKnife(pLevel, player, pStack);
                        thrownBoneKnife.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                        if (player.getAbilities().instabuild) {
                            thrownBoneKnife.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        pLevel.addFreshEntity(thrownBoneKnife);
                        pLevel.playSound(null, thrownBoneKnife, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                        if (!player.getAbilities().instabuild) {
                            player.getInventory().removeItem(pStack);
                        }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }
}
