package com.thomasglasser.minejago.core.particles;

import com.mojang.serialization.Codec;
import com.thomasglasser.minejago.MinejagoMod;
import com.thomasglasser.minejago.client.particle.SpinjitzuParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoParticleTypes
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MinejagoMod.MODID);

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
