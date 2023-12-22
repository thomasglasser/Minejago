package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.renderer.entity.layers.LayersConfig;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.focus.FocusConfig;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MinejagoClientConfig
{
    public static void register()
    {
        ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

        LayersConfig.registerClient(CLIENT_BUILDER);
        FocusConfig.registerClient(CLIENT_BUILDER);

        Services.CONFIG.registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
