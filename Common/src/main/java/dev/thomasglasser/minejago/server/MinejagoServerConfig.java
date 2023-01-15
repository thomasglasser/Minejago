package dev.thomasglasser.minejago.server;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowersConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class MinejagoServerConfig
{
    public static void register()
    {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        MinejagoPowersConfig.registerServer(SERVER_BUILDER);

        Services.CONFIG.registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());
    }
}
