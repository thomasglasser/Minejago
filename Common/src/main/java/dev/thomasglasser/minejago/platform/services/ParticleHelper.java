package dev.thomasglasser.minejago.platform.services;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public interface ParticleHelper {
    SimpleParticleType simple(String name, PendingParticleFactory<SimpleParticleType> factory, boolean alwaysRender);

    <T extends ParticleOptions> void fabricRegister(ParticleType<T> type, PendingParticleFactory<T> factory);

    /**
     * A pending particle factory.
     *
     * @param <T> The type of particle effects this factory deals with.
     */
    @FunctionalInterface
    interface PendingParticleFactory<T extends ParticleOptions> {
        /**
         * Called to create a new particle factory.
         *
         * <p>Particle sprites will be loaded from domain:/particles/particle_name.json as per vanilla minecraft behavior.
         *
         * @param provider The sprite provider used to supply sprite textures when drawing the mod's particle.
         *
         * @return A new particle factory.
         */
        ParticleProvider<T> create(SpriteSet provider);
    }
}
