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
import dev.thomasglasser.tommylib.api.world.item.ModeledItem;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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

    public abstract boolean canPowerHandle(ResourceKey<Power> power, Level level);

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (MinejagoServerConfig.get().requireCompatiblePower.get()) {
            if (!canPowerHandle(player.getData(MinejagoAttachmentTypes.POWER).power(), level)) {
                if (MinejagoServerConfig.get().enableMalfunction.get() && !player.level().isClientSide && !player.getAbilities().instabuild) {
                    goCrazy(player);
                }
                if (this.getFailSound() != null) {
                    level.playSound(null, player.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide);
            }
        }
        FocusData focusData = player.getData(MinejagoAttachmentTypes.FOCUS);
        focusData.addExhaustion(FocusConstants.EXHAUSTION_GOLDEN_WEAPON);
        if (focusData.getFocusLevel() < FocusConstants.GOLDEN_WEAPON_LEVEL) {
            if (this.getFailSound() != null) {
                level.playSound(null, player.blockPosition(), getFailSound(), SoundSource.PLAYERS);
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(usedHand), level.isClientSide);
        }
        return doUse(level, player, usedHand);
    }

    protected abstract InteractionResultHolder<ItemStack> doUse(Level level, Player player, InteractionHand usedHand);

    @Override
    public final InteractionResult useOn(UseOnContext pContext) {
        if (MinejagoServerConfig.get().requireCompatiblePower.get()) {
            if (!canPowerHandle(pContext.getPlayer().getData(MinejagoAttachmentTypes.POWER).power(), pContext.getLevel())) {
                if (MinejagoServerConfig.get().enableMalfunction.get() && !pContext.getPlayer().level().isClientSide && !pContext.getPlayer().getAbilities().instabuild) {
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
        if (pLivingEntity instanceof Player player && MinejagoServerConfig.get().requireCompatiblePower.get()) {
            if (!canPowerHandle(pLivingEntity.getData(MinejagoAttachmentTypes.POWER).power(), pLevel)) {
                if (MinejagoServerConfig.get().enableMalfunction.get() && !player.level().isClientSide && !player.getAbilities().instabuild) {
                    goCrazy(player);
                }
                if (this.getFailSound() != null) {
                    pLevel.playSound(null, pLivingEntity.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
            }
        }
        Player player = pLivingEntity instanceof Player p ? p : null;
        FocusData focusData = player == null ? null : player.getData(MinejagoAttachmentTypes.FOCUS);
        if (focusData != null) {
            focusData.addExhaustion(FocusConstants.EXHAUSTION_GOLDEN_WEAPON);
        }
        doReleaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    protected abstract void doReleaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged);

    @Override
    public final void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (livingEntity instanceof Player player && MinejagoServerConfig.get().requireCompatiblePower.get()) {
            if (!canPowerHandle(livingEntity.getData(MinejagoAttachmentTypes.POWER).power(), level)) {
                if (MinejagoServerConfig.get().enableMalfunction.get() && !player.level().isClientSide && !player.getAbilities().instabuild) {
                    goCrazy(player);
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
