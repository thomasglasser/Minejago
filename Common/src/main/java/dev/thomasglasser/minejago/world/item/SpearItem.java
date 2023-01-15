package dev.thomasglasser.minejago.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.client.renderer.item.WoodenNunchucksRenderer;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownIronSpear;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class SpearItem extends ThrowableSwordItem implements IModeledItem {
    /** Modifiers applied when the item is in the mainhand of a user. */
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    private BlockEntityWithoutLevelRenderer bewlr;

    public SpearItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        float attackDamage = (float) pAttackDamageModifier + pTier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", pAttackSpeedModifier, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", pAttackSpeedModifier, AttributeModifier.Operation.ADDITION));
        if (Services.ITEM.getAttackRangeAttribute() != null) builder.put(Services.ITEM.getAttackRangeAttribute(), new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 2.0, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                if (!pLevel.isClientSide) {
                    pStack.hurtAndBreak(1, player, (p_43388_) -> {
                        p_43388_.broadcastBreakEvent(pEntityLiving.getUsedItemHand());
                    });
                    ThrownIronSpear thrownIronSpear = new ThrownIronSpear(pLevel, player, pStack);
                    thrownIronSpear.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                    if (player.getAbilities().instabuild) {
                        thrownIronSpear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    pLevel.addFreshEntity(thrownIronSpear);
                    pLevel.playSound(null, thrownIronSpear, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
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
