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
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class MinejagoMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Minejago.MOD_ID);

    // Beneficial
    public static final DeferredHolder<MobEffect, MobEffect> CURE = register("instant_cure", () -> new /*ExtendedMobEffect*/MobEffect(MobEffectCategory.BENEFICIAL, 16777215) {
        @Override
        public boolean isInstantenous() {
            return true;
        }

//        @Override
        public boolean tick(LivingEntity entity, @Nullable MobEffectInstance effectInstance, int amplifier) {
            if (!entity.level().isClientSide) {
                List<MobEffectInstance> effects = List.copyOf(entity.getActiveEffects().stream().filter(effect -> effect.getEffect().value() != this).toList());
                for (MobEffectInstance effect : effects) {
                    entity.removeEffect(effect.getEffect());
                    if (effect.getAmplifier() - (amplifier + 1) >= 0)
                        entity.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier() - (amplifier + 1)));
                }
            }
            return true;
        }

//        @Override
        public boolean shouldTickEffect(@Nullable MobEffectInstance effectInstance, @Nullable LivingEntity entity, int ticksRemaining, int amplifier) {
            return ticksRemaining >= 1;
        }
    });
    public static final DeferredHolder<MobEffect, MobEffect> HYPERFOCUS = register("hyperfocus", () -> new /*ExtendedMobEffect*/MobEffect(MobEffectCategory.BENEFICIAL, 0x207100) {
        @Override
        public boolean isInstantenous() {
            return true;
        }

//        @Override
        public boolean tick(LivingEntity entity, @Nullable MobEffectInstance effectInstance, int amplifier) {
            if (!entity.level().isClientSide())
                entity.getData(MinejagoAttachmentTypes.FOCUS).meditate(false, amplifier + 1, FocusConstants.FOCUS_SATURATION_MAX);
            return true;
        }

//        @Override
        public boolean shouldTickEffect(@Nullable MobEffectInstance effectInstance, @Nullable LivingEntity entity, int ticksRemaining, int amplifier) {
            return ticksRemaining >= 1;
        }
    });

    private static DeferredHolder<MobEffect, MobEffect> register(String name, Supplier<MobEffect> effect) {
        return MOB_EFFECTS.register(name, effect);
    }

    public static void init() {}
}
