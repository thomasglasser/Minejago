package dev.thomasglasser.minejago.world.effect;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MinejagoMobEffects
{
    public static final RegistrationProvider<MobEffect> MOB_EFFECTS = RegistrationProvider.get(Registries.MOB_EFFECT, Minejago.MOD_ID);

    public static final RegistryObject<MobEffect> ACACIA_TEA = noEffects("acacia_tea", 0x4d3c21);
    public static final RegistryObject<MobEffect> OAK_TEA = noEffects("oak_tea", 0x4d3b21);
    public static final RegistryObject<MobEffect> CHERRY_TEA = noEffects("cherry_tea", 0x4d3230);
    public static final RegistryObject<MobEffect> SPRUCE_TEA = noEffects("spruce_tea", 0x4d341c);
    public static final RegistryObject<MobEffect> MANGROVE_TEA = noEffects("mangrove_tea", 0x4d1719);
    public static final RegistryObject<MobEffect> JUNGLE_TEA = noEffects("jungle_tea", 0x4d3323);
    public static final RegistryObject<MobEffect> DARK_OAK_TEA = noEffects("dark_oak_tea", 0x33200f);
    public static final RegistryObject<MobEffect> BIRCH_TEA = noEffects("birch_tea", 0x4d432f);

    public static final RegistryObject<MobEffect> CURE = MOB_EFFECTS.register("instant_cure", () -> new InstantenousMobEffect(MobEffectCategory.BENEFICIAL, 16777215)
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

    private static RegistryObject<MobEffect> noEffects(String name, int color)
    {
        return MOB_EFFECTS.register(name, () -> new MinejagoMobEffects.EmptyMobEffect(color));
    }

    public static void init() {}

    public static class EmptyMobEffect extends MobEffect
    {

        public EmptyMobEffect(int color) {
            super(MobEffectCategory.NEUTRAL, color);
        }
    }
}
