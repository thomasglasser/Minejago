package dev.thomasglasser.minejago.core.particles;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.particle.BoltsParticle;
import dev.thomasglasser.minejago.client.particle.RocksParticle;
import dev.thomasglasser.minejago.client.particle.SnowsParticle;
import dev.thomasglasser.minejago.client.particle.SparklesParticle;
import dev.thomasglasser.minejago.client.particle.SparksParticle;
import dev.thomasglasser.minejago.client.particle.VaporsParticle;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class MinejagoParticleTypes
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Minejago.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARKS = register("sparks", () -> TommyLibServices.PARTICLE.simple("sparks", SparksParticle.Provider::new, false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWS = register("snows", () -> TommyLibServices.PARTICLE.simple("snows", SnowsParticle.Provider::new,  false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ROCKS = register("rocks", () -> TommyLibServices.PARTICLE.simple("rocks", RocksParticle.Provider::new,  false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BOLTS = register("bolts", () -> TommyLibServices.PARTICLE.simple("bolts", BoltsParticle.Provider::new,  false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SPARKLES = register("sparkles", () -> TommyLibServices.PARTICLE.simple("sparkles", SparklesParticle.Provider::new,  false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VAPORS = register("vapors", () -> TommyLibServices.PARTICLE.simple("vapors", VaporsParticle.Provider::new,  false));
    
    private static <T extends ParticleType<?>> DeferredHolder<ParticleType<?>, T> register(String name, Supplier<T> RegistryObject)
    {
        return PARTICLE_TYPES.register(name, RegistryObject);
    }

    public static void init() {}
}
