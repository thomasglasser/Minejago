package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownIronSpear;
import dev.thomasglasser.tommylib.api.world.item.BaseModeledThrowableSwordItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

import static dev.thomasglasser.minejago.world.item.MinejagoItems.MOD_NEEDED;

public class SpearItem extends BaseModeledThrowableSwordItem
{
    public static final UUID BASE_ENTITY_INTERACTION_RANGE_UUID = UUID.fromString("44b34910-78b5-4c52-b796-b22b353ded46");
    public static final UUID BASE_BLOCK_INTERACTION_RANGE_UUID = UUID.fromString("03058db7-bcab-40d3-8ab7-c0271c1981a4");
    public static final UUID BASE_ATTACK_KNOCKBACK_UUID = UUID.fromString("7de62a30-d1e9-495a-b516-ce5e1568cacd");

    public SpearItem(Tier pTier, Properties pProperties) {
        super(pTier, pProperties);
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, int damage, float speed, float knockback)
    {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (float) damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", speed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_UUID, "Weapon modifier", 2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BASE_BLOCK_INTERACTION_RANGE_UUID, "Weapon modifier", 2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(BASE_ATTACK_KNOCKBACK_UUID, "Weapon modifier", knockback, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i >= 10) {
                if (!pLevel.isClientSide) {
                    pStack.hurtAndBreak(1, player, LivingEntity.getEquipmentSlotForItem(pStack));
                    ThrownIronSpear thrownIronSpear = new ThrownIronSpear(pLevel, player, pStack);
                    thrownIronSpear.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                    if (player.getAbilities().instabuild) {
                        thrownIronSpear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }

                    pLevel.addFreshEntity(thrownIronSpear);
                    pLevel.playSound(null, thrownIronSpear, MinejagoSoundEvents.SPEAR_THROW.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
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
        return Minejago.getBewlr();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        if (tooltipFlag.isAdvanced())
        {
            if (!Minejago.Dependencies.REACH_ENTITY_ATTRIBUTES.isInstalled())
                list.add(Component.translatable(MOD_NEEDED, Minejago.Dependencies.REACH_ENTITY_ATTRIBUTES.getModId()).withStyle(ChatFormatting.RED));
        }
    }
}
