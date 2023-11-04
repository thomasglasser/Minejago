package dev.thomasglasser.minejago.server;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowersConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class MinejagoServerConfig
{
    public static ForgeConfigSpec.BooleanValue ENABLE_TECH;
    public static ForgeConfigSpec.BooleanValue ENABLE_SKULKIN_RAIDS;

    public static void register()
    {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        registerFeatureToggles(SERVER_BUILDER);
        MinejagoPowersConfig.registerServer(SERVER_BUILDER);

        Services.CONFIG.registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }

    private static void registerFeatureToggles(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Optional features that enhance the mod, but may not match the desired experience of some players").push("features");

        ENABLE_TECH = builder
                .comment("Enable the technology of the mod, such as vehicles and computers")
                .define("enable_tech", true);

        ENABLE_SKULKIN_RAIDS = builder
                .comment("Enable Skulkin Raids on Four Weapons structures")
                .define("enable_skulkin_raids", true);

        builder.pop();
    }
}
