package dev.thomasglasser.minejago.core.particles;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class MinejagoParticleTypes
{
    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(BuiltInRegistries.PARTICLE_TYPE, Minejago.MOD_ID);

    public static final RegistryObject<ParticleType<SpinjitzuParticleOptions>> SPINJITZU = PARTICLE_TYPES.register("spinjitzu", () ->
            new ParticleType<>(true, SpinjitzuParticleOptions.DESERIALIZER) {
                @Override
                public Codec<SpinjitzuParticleOptions> codec() {
                    return SpinjitzuParticleOptions.CODEC;
                }
            });

    public static final RegistryObject<SimpleParticleType> SPARKS = PARTICLE_TYPES.register("sparks", () -> Services.PARTICLE.simple(false));
    public static final RegistryObject<SimpleParticleType> SNOWS = PARTICLE_TYPES.register("snows", () -> Services.PARTICLE.simple(false));
    public static final RegistryObject<SimpleParticleType> ROCKS = PARTICLE_TYPES.register("rocks", () -> Services.PARTICLE.simple(false));
    public static final RegistryObject<SimpleParticleType> BOLTS = PARTICLE_TYPES.register("bolts", () -> Services.PARTICLE.simple(false));
    public static final RegistryObject<SimpleParticleType> SPARKLES = PARTICLE_TYPES.register("sparkles", () -> Services.PARTICLE.simple(false));

    public static void init() {}
}
