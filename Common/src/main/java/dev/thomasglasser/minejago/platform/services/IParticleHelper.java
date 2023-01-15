package dev.thomasglasser.minejago.platform.services;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public interface IParticleHelper {
    SimpleParticleType simple(String name, boolean alwaysRender);

    void fabricRegister(String name, ParticleType type);
}
