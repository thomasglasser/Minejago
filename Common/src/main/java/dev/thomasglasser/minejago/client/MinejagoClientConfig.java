package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.renderer.entity.layers.LayersConfig;
import dev.thomasglasser.minejago.platform.Services;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class MinejagoClientConfig
{
    public static void register()
    {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        LayersConfig.registerClient(CLIENT_BUILDER);

        Services.CONFIG.registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
