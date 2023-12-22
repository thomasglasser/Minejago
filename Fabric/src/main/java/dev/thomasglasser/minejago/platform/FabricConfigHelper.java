package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.ConfigHelper;
import fuzs.forgeconfigapiport.api.config.v3.ForgeConfigRegistry;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class FabricConfigHelper implements ConfigHelper {
    @Override
    public void registerConfig(ModConfig.Type type, ModConfigSpec spec) {
        ForgeConfigRegistry.INSTANCE.register(Minejago.MOD_ID, type, spec);
    }
}
