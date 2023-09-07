package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.ParticleHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class FabricParticleHelper implements ParticleHelper {
    @Override
    public SimpleParticleType simple(String name, PendingParticleFactory<SimpleParticleType> factory, boolean alwaysRender) {
        SimpleParticleType simple = FabricParticleTypes.simple(alwaysRender);
        fabricRegister(simple, factory);
        return simple;
    }

    @Override
    public <T extends ParticleOptions> void fabricRegister(ParticleType<T> type, PendingParticleFactory<T> factory)
    {
        ParticleFactoryRegistry.getInstance().register(type, factory::create);
    }
}
