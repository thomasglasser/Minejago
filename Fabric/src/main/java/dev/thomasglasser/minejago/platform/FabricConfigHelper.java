package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.IConfigHelper;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class FabricConfigHelper implements IConfigHelper {
    @Override
    public void registerConfig(ModConfig.Type type, ForgeConfigSpec spec) {
        ForgeConfigRegistry.INSTANCE.register(Minejago.MOD_ID, type, spec);
    }
}
