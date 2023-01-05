package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IParticleHelper;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class ForgeParticleHelper implements IParticleHelper {
    @Override
    public SimpleParticleType simple(String name, boolean alwaysRender) {
        return new SimpleParticleType(alwaysRender);
    }

    @Override
    public void fabricRegister(String name, ParticleType type) {}
}
