package dev.thomasglasser.minejago.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowersConfig;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.thomasglasser.minejago.world.item.MinejagoItems.MOD_NEEDED;

public abstract class GoldenWeaponItem extends SimpleFoiledItem
{
    public GoldenWeaponItem(Properties pProperties)
    {
        super(pProperties);
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return pDamageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    public abstract boolean canPowerHandle(Power power, Registry<Power> registry);

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 30, AttributeModifier.Operation.ADDITION));
        return slot == EquipmentSlot.MAINHAND ? builder.build() : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public final InteractionResult useOn(UseOnContext pContext) {
        if (MinejagoPowersConfig.REQUIRE_FOR_GOLDEN_WEAPON.get())
        {
            if (!canPowerHandle(MinejagoPowers.POWERS.get(pContext.getLevel().registryAccess()).get(Services.DATA.getPowerData(pContext.getPlayer()).power()), MinejagoPowers.POWERS.get(pContext.getLevel().registryAccess())))
            {
                if (MinejagoPowersConfig.WEAPON_GOES_CRAZY.get()) {
                    goCrazy(pContext.getPlayer());
                }
                if (this.getFailSound() != null)
                {
                    pContext.getLevel().playSound(null, pContext.getPlayer().blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return InteractionResult.CONSUME_PARTIAL;
            }
        }
        return doUseOn(pContext);
    }

    protected abstract InteractionResult doUseOn(UseOnContext context);

    @Override
    public final void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player &&  MinejagoPowersConfig.REQUIRE_FOR_GOLDEN_WEAPON.get())
        {
            if (!canPowerHandle(MinejagoPowers.POWERS.get(pLevel.registryAccess()).get(Services.DATA.getPowerData(pLivingEntity).power()), MinejagoPowers.POWERS.get(pLevel.registryAccess())))
            {
                if (MinejagoPowersConfig.WEAPON_GOES_CRAZY.get()) {
                    goCrazy((Player) pLivingEntity);
                }
                if (this.getFailSound() != null)
                {
                    pLevel.playSound(null, pLivingEntity.blockPosition(), getFailSound(), SoundSource.PLAYERS);
                }
                return;
            }
        }
        doReleaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    protected abstract void doReleaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged);

    @Override
    public final void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (livingEntity instanceof Player &&  MinejagoPowersConfig.REQUIRE_FOR_GOLDEN_WEAPON.get())
        {
            if (!canPowerHandle(MinejagoPowers.POWERS.get(level.registryAccess()).get(Services.DATA.getPowerData(livingEntity).power()), MinejagoPowers.POWERS.get(level.registryAccess())))
            {
                if (MinejagoPowersConfig.WEAPON_GOES_CRAZY.get()) {
                    goCrazy((Player) livingEntity);
                }
                if (this.getFailSound() != null)
                {
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

    public static void overload(LivingEntity entity)
    {
        // TODO: Weapons portal event
    }

    @Nullable
    public SoundEvent getFailSound()
    {
        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        if (isAdvanced.isAdvanced())
        {
            if (!Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
                tooltipComponents.add(Component.translatable(MOD_NEEDED, Minejago.Dependencies.PLAYER_ANIMATOR.getModId()).withStyle(ChatFormatting.RED));
            if (!Minejago.Dependencies.DYNAMIC_LIGHTS.isInstalled())
                tooltipComponents.add(Component.translatable(MOD_NEEDED, Minejago.Dependencies.DYNAMIC_LIGHTS.getModId()).withStyle(ChatFormatting.RED));
        }
    }
}
