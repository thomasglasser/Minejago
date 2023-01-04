package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IParticleHelper;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class FabricParticleHelper implements IParticleHelper {
    @Override
    public SimpleParticleType simple(boolean alwaysRender) {
        return FabricParticleTypes.simple(alwaysRender);
    }
}
