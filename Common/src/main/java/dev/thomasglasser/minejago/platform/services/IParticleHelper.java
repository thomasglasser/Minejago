package dev.thomasglasser.minejago.platform.services;

import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public interface IParticleHelper {
    SimpleParticleType simple(boolean alwaysRender);
}
