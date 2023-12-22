package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.ConfigHelper;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ForgeConfigHelper implements ConfigHelper {
    @Override
    public void registerConfig(ModConfig.Type type, ModConfigSpec spec) {
        ModLoadingContext.get().registerConfig(type, spec);
    }
}
