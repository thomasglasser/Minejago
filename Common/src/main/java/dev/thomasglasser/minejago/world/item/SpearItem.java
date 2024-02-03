package dev.thomasglasser.minejago.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownIronSpear;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.world.item.ModeledItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.thomasglasser.minejago.world.item.MinejagoItems.MOD_NEEDED;

public class SpearItem extends ThrowableSwordItem implements ModeledItem
{
    /** Modifiers applied when the item is in the mainhand of a user. */
    private final ImmutableMultimap<Attribute, AttributeModifier> defaultModifiers;

    private BlockEntityWithoutLevelRenderer bewlr;

    public SpearItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, float knockback, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier("Weapon modifier", knockback, AttributeModifier.Operation.ADDITION));
        if (TommyLibServices.ITEM.getAttackRangeAttribute() != null) builder.put(TommyLibServices.ITEM.getAttackRangeAttribute(), new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 2.0, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND)).build();
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
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
        if (bewlr == null) bewlr = new MinejagoBlockEntityWithoutLevelRenderer();
        return bewlr;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        if (isAdvanced.isAdvanced())
        {
            if (!Minejago.Dependencies.REACH_ENTITY_ATTRIBUTES.isInstalled())
                tooltipComponents.add(Component.translatable(MOD_NEEDED, Minejago.Dependencies.REACH_ENTITY_ATTRIBUTES.getModId()).withStyle(ChatFormatting.RED));
        }
    }
}
