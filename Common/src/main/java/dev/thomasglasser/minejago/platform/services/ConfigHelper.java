package dev.thomasglasser.minejago.platform.services;

import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public interface ConfigHelper {
    void registerConfig(ModConfig.Type type, ModConfigSpec spec);
}
