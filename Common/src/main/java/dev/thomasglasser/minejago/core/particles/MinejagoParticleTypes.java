package dev.thomasglasser.minejago.core.particles;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.particle.*;
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
    {
        ParticleType<SpinjitzuParticleOptions> particle = new ParticleType<>(true, SpinjitzuParticleOptions.DESERIALIZER)
        {
            @Override
            public Codec<SpinjitzuParticleOptions> codec() {
                return SpinjitzuParticleOptions.CODEC;
            }
        };
        Services.PARTICLE.fabricRegister("spinjitzu", particle);
        return particle;
    });

    public static final RegistryObject<SimpleParticleType> SPARKS = PARTICLE_TYPES.register("sparks", () -> Services.PARTICLE.simple("sparks", false));
    public static final RegistryObject<SimpleParticleType> SNOWS = PARTICLE_TYPES.register("snows", () -> Services.PARTICLE.simple("snows", false));
    public static final RegistryObject<SimpleParticleType> ROCKS = PARTICLE_TYPES.register("rocks", () -> Services.PARTICLE.simple("rocks", false));
    public static final RegistryObject<SimpleParticleType> BOLTS = PARTICLE_TYPES.register("bolts", () -> Services.PARTICLE.simple("bolts", false));
    public static final RegistryObject<SimpleParticleType> SPARKLES = PARTICLE_TYPES.register("sparkles", () -> Services.PARTICLE.simple("sparkles", false));

    public static void init() {}
}
