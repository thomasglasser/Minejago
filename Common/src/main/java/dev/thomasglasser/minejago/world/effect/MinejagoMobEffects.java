package dev.thomasglasser.minejago.world.effect;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoLevelUtils;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class MinejagoMobEffects
{
    public static final Supplier<MobEffect> ACACIA_TEA = noEffects("acacia_tea", 0x4d3c21);
    public static final Supplier<MobEffect> OAK_TEA = noEffects("oak_tea", 0x4d3b21);
    public static final Supplier<MobEffect> CHERRY_TEA = noEffects("cherry_tea", 0x4d3230);
    public static final Supplier<MobEffect> SPRUCE_TEA = noEffects("spruce_tea", 0x4d341c);
    public static final Supplier<MobEffect> MANGROVE_TEA = noEffects("mangrove_tea", 0x4d1719);
    public static final Supplier<MobEffect> JUNGLE_TEA = noEffects("jungle_tea", 0x4d3323);
    public static final Supplier<MobEffect> DARK_OAK_TEA = noEffects("dark_oak_tea", 0x33200f);
    public static final Supplier<MobEffect> BIRCH_TEA = noEffects("birch_tea", 0x4d432f);

    // Beneficial
    public static final Supplier<MobEffect> CURE = register("instant_cure", () -> new InstantenousMobEffect(MobEffectCategory.BENEFICIAL, 16777215)
    {
        @Override
        public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, int pAmplifier, double pHealth) {
            List<MobEffectInstance> effects = pLivingEntity.getActiveEffects().stream().toList();
            pLivingEntity.removeAllEffects();
            for (MobEffectInstance effect : effects)
            {
                if (effect.getAmplifier() - (pAmplifier + 1) >= 0) pLivingEntity.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier() - (pAmplifier + 1)));
            }
        }
    });
    public static final Supplier<MobEffect> HYPERFOCUS = register("hyperfocus", () -> new InstantenousMobEffect(MobEffectCategory.BENEFICIAL, 0x207100)
    {
        @Override
        public void applyEffectTick(LivingEntity livingEntity, int amplifier)
        {
            if (!livingEntity.level().isClientSide && livingEntity instanceof Player player) {
                ((FocusDataHolder)player).getFocusData().increase(amplifier + 1, FocusConstants.FOCUS_SATURATION_MAX);
            }
        }

        @Override
        public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health)
        {
            if (!livingEntity.level().isClientSide && livingEntity instanceof Player player) {
                ((FocusDataHolder)player).getFocusData().increase(amplifier + 1, FocusConstants.FOCUS_SATURATION_MAX);
            }
        }
    });

    // Neutral
    public static final Supplier<MobEffect> SKULKINS_CURSE = register("skulkins_curse", () -> new MobEffect(MobEffectCategory.NEUTRAL, 0xAD282D) {
        @Override
        public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
            if (livingEntity instanceof ServerPlayer serverPlayer && !livingEntity.isSpectator()) {
                ServerLevel serverLevel = serverPlayer.serverLevel();
                if (serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
                    return;
                }

                if (MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(serverPlayer, 16)) {
                    ((SkulkinRaidsHolder)serverLevel).getSkulkinRaids().createOrExtendSkulkinRaid(serverPlayer);
                }
            }
        }
    });

    private static Supplier<MobEffect> register(String name, Supplier<MobEffect> effect)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.MOB_EFFECT, name, effect);
    }

    private static Supplier<MobEffect> noEffects(String name, int color)
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
