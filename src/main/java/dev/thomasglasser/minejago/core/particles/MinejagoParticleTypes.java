package dev.thomasglasser.minejago.core.particles;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoParticleTypes
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Minejago.MODID);

    public static final RegistryObject<ParticleType<SpinjitzuParticleOptions>> SPINJITZU = PARTICLE_TYPES.register("spinjitzu", () ->
    {
        return new ParticleType<SpinjitzuParticleOptions>(true, SpinjitzuParticleOptions.DESERIALIZER) {
            @Override
            public Codec<SpinjitzuParticleOptions> codec() {
                return SpinjitzuParticleOptions.CODEC;
            }
        };
    });
}
