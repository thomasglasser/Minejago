package dev.thomasglasser.minejago.core.particles;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.client.particle.BoltsParticle;
import dev.thomasglasser.minejago.client.particle.RocksParticle;
import dev.thomasglasser.minejago.client.particle.SnowsParticle;
import dev.thomasglasser.minejago.client.particle.SparklesParticle;
import dev.thomasglasser.minejago.client.particle.SparksParticle;
import dev.thomasglasser.minejago.client.particle.SpinjitzuParticle;
import dev.thomasglasser.minejago.client.particle.VaporsParticle;
import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class MinejagoParticleTypes
{

    public static final Supplier<ParticleType<SpinjitzuParticleOptions>> SPINJITZU = register("spinjitzu", () ->
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

    public static final Supplier<SimpleParticleType> SPARKS = register("sparks", () -> Services.PARTICLE.simple("sparks", SparksParticle.Provider::new, false));
    public static final Supplier<SimpleParticleType> SNOWS = register("snows", () -> Services.PARTICLE.simple("snows", SnowsParticle.Provider::new,  false));
    public static final Supplier<SimpleParticleType> ROCKS = register("rocks", () -> Services.PARTICLE.simple("rocks", RocksParticle.Provider::new,  false));
    public static final Supplier<SimpleParticleType> BOLTS = register("bolts", () -> Services.PARTICLE.simple("bolts", BoltsParticle.Provider::new,  false));
    public static final Supplier<SimpleParticleType> SPARKLES = register("sparkles", () -> Services.PARTICLE.simple("sparkles", SparklesParticle.Provider::new,  false));
    public static final Supplier<SimpleParticleType> VAPORS = register("vapors", () -> Services.PARTICLE.simple("vapors", VaporsParticle.Provider::new,  false));
    
    private static <T extends ParticleType<?>> Supplier<T> register(String name, Supplier<T> supplier)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.PARTICLE_TYPE, name, supplier);
    }

    public static void init() {}
}
