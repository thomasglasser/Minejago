package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.client.particle.*;
import dev.thomasglasser.minejago.platform.services.IParticleHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class FabricParticleHelper implements IParticleHelper {
    @Override
    public SimpleParticleType simple(String name, boolean alwaysRender) {
        SimpleParticleType simple = FabricParticleTypes.simple(alwaysRender);
        fabricRegister(name, simple);
        return simple;
    }

    public void fabricRegister(String name, ParticleType type)
    {
        ParticleFactoryRegistry.getInstance().register(type, getPendingFactory(name));
    }

    private ParticleFactoryRegistry.PendingParticleFactory getPendingFactory(String name)
    {

        return switch (name)
        {
            case "bolts" -> BoltsParticle.Provider::new;
            case "sparks" -> SparksParticle.Provider::new;
            case "snows" -> SnowsParticle.Provider::new;
            case "rocks" -> RocksParticle.Provider::new;
            case "sparkles" -> SparklesParticle.Provider::new;
            case "spinjitzu" -> SpinjitzuParticle.Provider::new;
            case "vapors" -> VaporsParticle.Provider::new;

            default -> null;
        };
    }
}
