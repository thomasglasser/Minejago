package dev.thomasglasser.minejago.client.renderer.entity.layers;

import net.minecraftforge.common.ForgeConfigSpec;

public class LayersConfig
{
    public static ForgeConfigSpec.BooleanValue DISPLAY_BETA;
    public static ForgeConfigSpec.EnumValue<BetaTesterLayerOptions> BETA_CHOICE;

    public static ForgeConfigSpec.BooleanValue DISPLAY_DEV;

    public static void registerClient(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Settings for player layers").push("layers");

        DISPLAY_BETA = builder
                .comment("Display your preferred beta layer (if unlocked)")
                .define("beta", false);
        BETA_CHOICE = builder
                .comment("The beta layer to be displayed (if unlocked)")
                .defineEnum("choice", BetaTesterLayerOptions.BAMBOO_HAT);

        DISPLAY_DEV = builder
                .comment("Display the dev layer (if unlocked)")
                .define("dev", false);

        builder.pop();
    }
}
