package dev.thomasglasser.minejago.world.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowersConfig;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.core.Registry;
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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

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
        return pDamageSource == DamageSource.OUT_OF_WORLD;
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
            if (!canPowerHandle(MinejagoPowers.POWERS.apply(pContext.getLevel().registryAccess()).get(Services.DATA.getPowerData(pContext.getPlayer()).power()), MinejagoPowers.POWERS.apply(pContext.getLevel().registryAccess())))
            {
                if (MinejagoPowersConfig.WEAPON_GOES_CRAZY.get()) {
                    goCrazy(pContext.getPlayer());
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
            if (!canPowerHandle(MinejagoPowers.POWERS.apply(pLevel.registryAccess()).get(Services.DATA.getPowerData(pLivingEntity).power()), MinejagoPowers.POWERS.apply(pLevel.registryAccess())))
            {
                if (MinejagoPowersConfig.WEAPON_GOES_CRAZY.get()) {
                    goCrazy((Player) pLivingEntity);
                }
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
            if (!canPowerHandle(MinejagoPowers.POWERS.apply(level.registryAccess()).get(Services.DATA.getPowerData(livingEntity).power()), MinejagoPowers.POWERS.apply(level.registryAccess())))
            {
                if (MinejagoPowersConfig.WEAPON_GOES_CRAZY.get()) {
                    goCrazy((Player) livingEntity);
                }
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
}
