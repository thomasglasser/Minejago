package dev.thomasglasser.minejago.core.particles;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoParticleTypes
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Minejago.MOD_ID);

    public static final RegistryObject<ParticleType<SpinjitzuParticleOptions>> SPINJITZU = PARTICLE_TYPES.register("spinjitzu", () ->
            new ParticleType<>(true, SpinjitzuParticleOptions.DESERIALIZER) {
                @Override
                public Codec<SpinjitzuParticleOptions> codec() {
                    return SpinjitzuParticleOptions.CODEC;
                }
            });

    public static final RegistryObject<SimpleParticleType> SPARKS = PARTICLE_TYPES.register("sparks", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SNOWS = PARTICLE_TYPES.register("snows", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ROCKS = PARTICLE_TYPES.register("rocks", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BOLTS = PARTICLE_TYPES.register("bolts", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SPARKLES = PARTICLE_TYPES.register("sparkles", () -> new SimpleParticleType(false));
}
