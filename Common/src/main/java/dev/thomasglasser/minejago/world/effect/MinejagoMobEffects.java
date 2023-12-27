package dev.thomasglasser.minejago.world.effect;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.util.MinejagoLevelUtils;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.tslat.effectslib.api.ExtendedMobEffect;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class MinejagoMobEffects
{
    public static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(BuiltInRegistries.MOB_EFFECT, Minejago.MOD_ID);

    // Teas - Color is darkened light part of planks
    public static final RegistryObject<MobEffect> ACACIA_TEA = noEffects("acacia_tea", 0x4d3c21);
    public static final RegistryObject<MobEffect> OAK_TEA = noEffects("oak_tea", 0x4d3b21);
    public static final RegistryObject<MobEffect> CHERRY_TEA = noEffects("cherry_tea", 0x4d3230);
    public static final RegistryObject<MobEffect> SPRUCE_TEA = noEffects("spruce_tea", 0x4d341c);
    public static final RegistryObject<MobEffect> MANGROVE_TEA = noEffects("mangrove_tea", 0x4d1719);
    public static final RegistryObject<MobEffect> JUNGLE_TEA = noEffects("jungle_tea", 0x4d3323);
    public static final RegistryObject<MobEffect> DARK_OAK_TEA = noEffects("dark_oak_tea", 0x33200f);
    public static final RegistryObject<MobEffect> BIRCH_TEA = noEffects("birch_tea", 0x4d432f);
    public static final RegistryObject<MobEffect> AZALEA_TEA = noEffects("azalea_tea", 0x3f4a2b);
    public static final RegistryObject<MobEffect> FLOWERING_AZALEA_TEA = noEffects("flowering_azalea_tea", 0x592f62);

    // Beneficial
    public static final RegistryObject<MobEffect> CURE = register("instant_cure", () -> new ExtendedMobEffect(MobEffectCategory.BENEFICIAL, 16777215)
    {
        @Override
        public boolean isInstantenous()
        {
            return true;
        }

        @Override
        public void onApplication(@Nullable MobEffectInstance effectInstance, @Nullable Entity source, LivingEntity entity, int amplifier)
        {
            super.onApplication(effectInstance, source, entity, amplifier);
            List<MobEffectInstance> effects = entity.getActiveEffects().stream().toList();
            entity.removeAllEffects();
            for (MobEffectInstance effect : effects)
            {
                if (effect.getAmplifier() - (amplifier + 1) >= 0) entity.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier() - (amplifier + 1)));
            }
        }
    });
    public static final RegistryObject<MobEffect> HYPERFOCUS = register("hyperfocus", () -> new ExtendedMobEffect(MobEffectCategory.BENEFICIAL, 0x207100)
    {
        @Override
        public boolean isInstantenous()
        {
            return true;
        }

        @Override
        public void tick(LivingEntity entity, @Nullable MobEffectInstance effectInstance, int amplifier)
        {
            super.tick(entity, effectInstance, amplifier);
            onApplication(effectInstance, null, entity, amplifier);
        }

        @Override
        public void onApplication(@Nullable MobEffectInstance effectInstance, @Nullable Entity source, LivingEntity entity, int amplifier)
        {
            super.onApplication(effectInstance, source, entity, amplifier);
            if (!entity.level().isClientSide && entity instanceof Player player) {
                ((FocusDataHolder)player).getFocusData().increase(amplifier + 1, FocusConstants.FOCUS_SATURATION_MAX);
            }
        }

        @Override
        public boolean shouldTickEffect(@Nullable MobEffectInstance effectInstance, @Nullable LivingEntity entity, int ticksRemaining, int amplifier)
        {
            return effectInstance != null && effectInstance.getDuration() > 1;
        }
    });

    // Neutral
    public static final RegistryObject<MobEffect> SKULKINS_CURSE = register("skulkins_curse", () -> new ExtendedMobEffect(MobEffectCategory.NEUTRAL, 0xAD282D) {
        @Override
        public void tick(LivingEntity entity, @Nullable MobEffectInstance effectInstance, int amplifier)
        {
            super.tick(entity, effectInstance, amplifier);
            if (entity instanceof ServerPlayer serverPlayer && !serverPlayer.isSpectator()) {
                ServerLevel serverLevel = serverPlayer.serverLevel();
                if (serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
                    return;
                }

                if (MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(serverPlayer, 16)) {
                    ((SkulkinRaidsHolder)serverLevel).getSkulkinRaids().createOrExtendSkulkinRaid(serverPlayer);
                }
            }
        }

        @Override
        public boolean shouldTickEffect(@Nullable MobEffectInstance effectInstance, @Nullable LivingEntity entity, int ticksRemaining, int amplifier)
        {
            return true;
        }
    });

    private static RegistryObject<MobEffect> register(String name, Supplier<MobEffect> effect)
    {
        return MOB_EFFECTS.register(name, effect);
    }

    private static RegistryObject<MobEffect> noEffects(String name, int color)
    {
        return register(name, () -> new MinejagoMobEffects.EmptyMobEffect(color));
    }

    public static void init() {}

    public static class EmptyMobEffect extends MobEffect
    {

        public EmptyMobEffect(int color) {
            super(MobEffectCategory.NEUTRAL, color);
        }
    }
}
