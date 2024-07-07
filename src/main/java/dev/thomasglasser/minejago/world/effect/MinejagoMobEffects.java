package dev.thomasglasser.minejago.world.effect;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.tslat.effectslib.api.ExtendedMobEffect;
import org.jetbrains.annotations.Nullable;

public class MinejagoMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Minejago.MOD_ID);

    // Beneficial
    public static final DeferredHolder<MobEffect, MobEffect> CURE = register("instant_cure", () -> new ExtendedMobEffect(MobEffectCategory.BENEFICIAL, 16777215) {
        @Override
        public boolean isInstantenous() {
            return true;
        }

        @Override
        public void onApplication(@Nullable MobEffectInstance effectInstance, @Nullable Entity source, LivingEntity entity, int amplifier) {
            super.onApplication(effectInstance, source, entity, amplifier);
            List<MobEffectInstance> effects = entity.getActiveEffects().stream().toList();
            entity.removeAllEffects();
            for (MobEffectInstance effect : effects) {
                if (effect.getAmplifier() - (amplifier + 1) >= 0) entity.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier() - (amplifier + 1)));
            }
        }
    });
    public static final DeferredHolder<MobEffect, MobEffect> HYPERFOCUS = register("hyperfocus", () -> new ExtendedMobEffect(MobEffectCategory.BENEFICIAL, 0x207100) {
        @Override
        public boolean isInstantenous() {
            return true;
        }

        @Override
        public boolean tick(LivingEntity entity, @Nullable MobEffectInstance effectInstance, int amplifier) {
            onApplication(effectInstance, null, entity, amplifier);
            return super.tick(entity, effectInstance, amplifier);
        }

        @Override
        public void onApplication(@Nullable MobEffectInstance effectInstance, @Nullable Entity source, LivingEntity entity, int amplifier) {
            super.onApplication(effectInstance, source, entity, amplifier);
            if (!entity.level().isClientSide && entity instanceof Player player)
                player.getData(MinejagoAttachmentTypes.FOCUS).meditate(false, amplifier + 1, FocusConstants.FOCUS_SATURATION_MAX);
        }

        @Override
        public boolean shouldTickEffect(@Nullable MobEffectInstance effectInstance, @Nullable LivingEntity entity, int ticksRemaining, int amplifier) {
            return effectInstance != null && effectInstance.getDuration() > 1;
        }
    });

    private static DeferredHolder<MobEffect, MobEffect> register(String name, Supplier<MobEffect> effect) {
        return MOB_EFFECTS.register(name, effect);
    }

    public static void init() {}
}
