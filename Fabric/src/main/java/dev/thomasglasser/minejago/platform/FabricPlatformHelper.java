package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.PlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isClientSide()
    {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
