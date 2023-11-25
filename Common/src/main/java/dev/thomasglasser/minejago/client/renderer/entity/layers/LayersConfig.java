package dev.thomasglasser.minejago.client.renderer.entity.layers;

import net.minecraftforge.common.ForgeConfigSpec;

public class LayersConfig
{
    public static ForgeConfigSpec.BooleanValue DISPLAY_SNAPSHOT;
    public static ForgeConfigSpec.EnumValue<SnapshotTesterLayerOptions> SNAPSHOT_CHOICE;

    public static ForgeConfigSpec.BooleanValue DISPLAY_DEV;
    public static ForgeConfigSpec.BooleanValue DISPLAY_OG_DEV;

    public static void registerClient(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Settings for player layers").push("layers");

        DISPLAY_SNAPSHOT = builder
                .comment("Display your preferred Snapshot Tester layer (if unlocked)")
                .define("snapshot", false);
        SNAPSHOT_CHOICE = builder
                .comment("The Snapshot Tester layer to be displayed (if unlocked)")
                .defineEnum("choice", SnapshotTesterLayerOptions.BAMBOO_HAT);

        DISPLAY_DEV = builder
                .comment("Display the Dev Team layer (if unlocked)")
                .define("dev", false);
        DISPLAY_OG_DEV = builder
                .comment("Display the OG Dev Team layer (if unlocked)")
                .define("og_dev", false);

        builder.pop();
    }
}
