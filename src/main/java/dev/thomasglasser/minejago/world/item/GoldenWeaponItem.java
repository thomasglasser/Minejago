package dev.thomasglasser.minejago.world.item;

import static dev.thomasglasser.minejago.world.item.MinejagoItems.MOD_NEEDED;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.tommylib.api.client.renderer.BewlrProvider;
import dev.thomasglasser.tommylib.api.world.item.BaseModeledItem;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public abstract class GoldenWeaponItem extends BaseModeledItem {
    public GoldenWeaponItem(Properties pProperties) {
        super(pProperties.rarity(Rarity.EPIC).fireResistant().stacksTo(1)
                .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                .component(DataComponents.MAX_DAMAGE, 0)
                .component(DataComponents.UNBREAKABLE, new Unbreakable(true))
                .component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(SwordItem.BASE_ATTACK_DAMAGE_ID, 30, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()));
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    public abstract boolean canPowerHandle(ResourceKey<Power> power, Level level);

    @Override
    public final InteractionResult useOn(UseOnContext pContext) {
        if (MinejagoServerConfig.INSTANCE.requireCompatiblePower.get()) {
            if (!canPowerHandle(pContext.getPlayer().getData(MinejagoAttachmentTypes.POWER).power(), pContext.getLevel())) {
                if (MinejagoServerConfig.INSTANCE.enableMalfunction.get()) {
                    goCrazy(pContext.getPlayer());
                }
                if (this.getFailSound() != null) {
                    pContext.getLevel().playSound(null, pContext.getPlayer().blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return pContext.getLevel().isClientSide ? InteractionResult.SUCCESS : InteractionResult.FAIL;
            }
        }
        Player player = pContext.getPlayer();
        FocusData focusData = player == null ? null : player.getData(MinejagoAttachmentTypes.FOCUS);
        if (focusData != null) {
            focusData.addExhaustion(FocusConstants.EXHAUSTION_GOLDEN_WEAPON);
            if (focusData.getFocusLevel() < FocusConstants.GOLDEN_WEAPON_LEVEL) {
                if (this.getFailSound() != null) {
                    pContext.getLevel().playSound(null, player.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return pContext.getLevel().isClientSide ? InteractionResult.SUCCESS : InteractionResult.FAIL;
            }
        }
        return doUseOn(pContext);
    }

    protected abstract InteractionResult doUseOn(UseOnContext context);

    @Override
    public final void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player && MinejagoServerConfig.INSTANCE.requireCompatiblePower.get()) {
            if (!canPowerHandle(pLivingEntity.getData(MinejagoAttachmentTypes.POWER).power(), pLevel)) {
                if (MinejagoServerConfig.INSTANCE.enableMalfunction.get()) {
                    goCrazy((Player) pLivingEntity);
                }
                if (this.getFailSound() != null) {
                    pLevel.playSound(null, pLivingEntity.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return;
            }
        }
        Player player = pLivingEntity instanceof Player p ? p : null;
        FocusData focusData = player == null ? null : player.getData(MinejagoAttachmentTypes.FOCUS);
        if (focusData != null) {
            focusData.addExhaustion(FocusConstants.EXHAUSTION_GOLDEN_WEAPON);
        }
        doReleaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    protected abstract void doReleaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged);

    @Override
    public final void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (livingEntity instanceof Player && MinejagoServerConfig.INSTANCE.requireCompatiblePower.get()) {
            if (!canPowerHandle(livingEntity.getData(MinejagoAttachmentTypes.POWER).power(), level)) {
                if (MinejagoServerConfig.INSTANCE.enableMalfunction.get()) {
                    goCrazy((Player) livingEntity);
                }
                if (this.getFailSound() != null) {
                    level.playSound(null, livingEntity.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return;
            }
        }
        Player player = livingEntity instanceof Player p ? p : null;
        FocusData focusData = player == null ? null : player.getData(MinejagoAttachmentTypes.FOCUS);
        if (focusData != null) {
            focusData.addExhaustion(FocusConstants.EXHAUSTION_GOLDEN_WEAPON);
            if (focusData.getFocusLevel() < FocusConstants.GOLDEN_WEAPON_LEVEL) {
                if (this.getFailSound() != null) {
                    level.playSound(null, livingEntity.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return;
            }
        }
        doOnUsingTick(stack, livingEntity, remainingUseDuration);
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    protected abstract void doOnUsingTick(ItemStack stack, LivingEntity player, int count);

    protected abstract void goCrazy(Player player);

    public static void overload(LivingEntity entity) {
        // TODO: Weapons portal event
    }

    public SoundEvent getFailSound() {
        return null;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        if (tooltipFlag.isAdvanced()) {
            if (!Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
                list.add(Component.translatable(MOD_NEEDED, Minejago.Dependencies.PLAYER_ANIMATOR.getModId()).withStyle(ChatFormatting.RED));
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
