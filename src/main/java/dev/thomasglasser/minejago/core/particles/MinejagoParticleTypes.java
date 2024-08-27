package dev.thomasglasser.minejago.core.particles;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class MinejagoParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Minejago.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARKS = register("sparks", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWS = register("snows", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ROCKS = register("rocks", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BOLTS = register("bolts", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARKLES = register("sparkles", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VAPORS = register("vapors", () -> new SimpleParticleType(false));

    private static <T extends ParticleType<?>> DeferredHolder<ParticleType<?>, T> register(String name, Supplier<T> RegistryObject) {
        return PARTICLE_TYPES.register(name, RegistryObject);
    }

    public static void init() {}
}
