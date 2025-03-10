package dev.thomasglasser.minejago.world.item;

import static dev.thomasglasser.minejago.world.item.MinejagoItems.MOD_NEEDED;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.tommylib.api.client.renderer.BewlrProvider;
import dev.thomasglasser.tommylib.api.world.item.ModeledItem;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public abstract class GoldenWeaponItem extends Item implements ModeledItem {
    public GoldenWeaponItem(Properties pProperties) {
        super(pProperties.rarity(Rarity.RARE).stacksTo(1)
//                .component(DataComponents.TOOLTIP_STYLE, Minejago.modLoc("golden_weapon"))
                .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
//                .component(DataComponents.DAMAGE_RESISTANT, new DamageResistant(MinejagoDamageTypeTags.RESISTED_BY_GOLDEN_WEAPONS))
                .component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(SwordItem.BASE_ATTACK_DAMAGE_ID, 30, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    @Override
    public final InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        FocusData focusData = player.getData(MinejagoAttachmentTypes.FOCUS);
        if (focusData.getFocusLevel() < FocusConstants.GOLDEN_WEAPON_LEVEL) {
            if (this.getFailSound() != null) {
                level.playSound(null, player.blockPosition(), getFailSound(), SoundSource.PLAYERS);
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide);
        }
        focusData.addExhaustion(FocusConstants.EXHAUSTION_GOLDEN_WEAPON);
        return doUse(level, player, usedHand);
    }

    protected abstract InteractionResultHolder<ItemStack> doUse(Level level, Player player, InteractionHand usedHand);

    @Override
    public final InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        FocusData focusData = player != null ? player.getData(MinejagoAttachmentTypes.FOCUS) : null;
        if (focusData != null) {
            if (focusData.getFocusLevel() < FocusConstants.GOLDEN_WEAPON_LEVEL) {
                if (this.getFailSound() != null) {
                    pContext.getLevel().playSound(null, player.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return pContext.getLevel().isClientSide ? InteractionResult.SUCCESS : InteractionResult.FAIL;
            }
            focusData.addExhaustion(FocusConstants.EXHAUSTION_GOLDEN_WEAPON);
        }
        return doUseOn(pContext);
    }

    protected abstract InteractionResult doUseOn(UseOnContext context);

    @Override
    public final void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        FocusData focusData = pLivingEntity instanceof Player p ? p.getData(MinejagoAttachmentTypes.FOCUS) : null;
        if (focusData != null) {
            if (focusData.getFocusLevel() < FocusConstants.GOLDEN_WEAPON_LEVEL) {
                if (this.getFailSound() != null) {
                    pLevel.playSound(null, pLivingEntity.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return;
            }
            focusData.addExhaustion(FocusConstants.EXHAUSTION_GOLDEN_WEAPON);
        }
        doReleaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    protected abstract void doReleaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged);

    @Override
    public final void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        FocusData focusData = livingEntity instanceof Player p ? p.getData(MinejagoAttachmentTypes.FOCUS) : null;
        if (focusData != null) {
            if (focusData.getFocusLevel() < FocusConstants.GOLDEN_WEAPON_LEVEL) {
                if (this.getFailSound() != null) {
                    level.playSound(null, livingEntity.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return;
            }
            focusData.addExhaustion(FocusConstants.EXHAUSTION_GOLDEN_WEAPON);
        }
        doOnUsingTick(stack, livingEntity, remainingUseDuration);
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    protected abstract void doOnUsingTick(ItemStack stack, LivingEntity player, int remainingUseDuration);

    public SoundEvent getFailSound() {
        return null;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        if (tooltipFlag.isAdvanced()) {
            if (!Minejago.Dependencies.RYOAMIC_LIGHTS.isInstalled())
                list.add(Component.translatable(MOD_NEEDED, Minejago.Dependencies.RYOAMIC_LIGHTS.getModId()).withStyle(ChatFormatting.RED));
        }
    }

    @Override
    public void createBewlrProvider(Consumer<BewlrProvider> consumer) {
        consumer.accept(new BewlrProvider() {
            @Override
            public BlockEntityWithoutLevelRenderer getBewlr() {
                return MinejagoClientUtils.getBewlr();
            }
        });
    }
}
