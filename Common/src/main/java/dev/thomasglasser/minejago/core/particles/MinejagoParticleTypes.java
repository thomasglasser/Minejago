package dev.thomasglasser.minejago.core.particles;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.particle.BoltsParticle;
import dev.thomasglasser.minejago.client.particle.RocksParticle;
import dev.thomasglasser.minejago.client.particle.SnowsParticle;
import dev.thomasglasser.minejago.client.particle.SparklesParticle;
import dev.thomasglasser.minejago.client.particle.SparksParticle;
import dev.thomasglasser.minejago.client.particle.SpinjitzuParticle;
import dev.thomasglasser.minejago.client.particle.VaporsParticle;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class MinejagoParticleTypes
{
    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(BuiltInRegistries.PARTICLE_TYPE, Minejago.MOD_ID);

    public static final RegistryObject<ParticleType<SpinjitzuParticleOptions>> SPINJITZU = register("spinjitzu", () ->
    {
        ParticleType<SpinjitzuParticleOptions> particle = new ParticleType<>(true, SpinjitzuParticleOptions.DESERIALIZER)
        {
            @Override
            public Codec<SpinjitzuParticleOptions> codec() {
                return SpinjitzuParticleOptions.CODEC;
            }
        };
        Services.PARTICLE.fabricRegister(particle, SpinjitzuParticle.Provider::new);
        return particle;
    });

    public static final RegistryObject<SimpleParticleType> SPARKS = register("sparks", () -> Services.PARTICLE.simple("sparks", SparksParticle.Provider::new, false));
    public static final RegistryObject<SimpleParticleType> SNOWS = register("snows", () -> Services.PARTICLE.simple("snows", SnowsParticle.Provider::new,  false));
    public static final RegistryObject<SimpleParticleType> ROCKS = register("rocks", () -> Services.PARTICLE.simple("rocks", RocksParticle.Provider::new,  false));
    public static final RegistryObject<SimpleParticleType> BOLTS = register("bolts", () -> Services.PARTICLE.simple("bolts", BoltsParticle.Provider::new,  false));
    public static final RegistryObject<SimpleParticleType> SPARKLES = register("sparkles", () -> Services.PARTICLE.simple("sparkles", SparklesParticle.Provider::new,  false));
    public static final RegistryObject<SimpleParticleType> VAPORS = register("vapors", () -> Services.PARTICLE.simple("vapors", VaporsParticle.Provider::new,  false));
    
    private static <T extends ParticleType<?>> RegistryObject<T> register(String name, Supplier<T> RegistryObject)
    {
        return PARTICLE_TYPES.register(name, RegistryObject);
    }

    public static void init() {}
}
