package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IParticleHelper;
import net.minecraft.core.particles.SimpleParticleType;

public class ForgeParticleHelper implements IParticleHelper {
    @Override
    public SimpleParticleType simple(boolean alwaysRender) {
        return new SimpleParticleType(alwaysRender);
    }
}
