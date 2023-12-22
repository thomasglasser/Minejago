package dev.thomasglasser.minejago.client.renderer.entity.layers;

import net.neoforged.neoforge.common.ModConfigSpec;

public class LayersConfig
{
    public static ModConfigSpec.BooleanValue DISPLAY_SNAPSHOT;
    public static ModConfigSpec.EnumValue<SnapshotTesterLayerOptions> SNAPSHOT_CHOICE;

    public static ModConfigSpec.BooleanValue DISPLAY_DEV;
    public static ModConfigSpec.BooleanValue DISPLAY_OG_DEV;

    public static void registerClient(ModConfigSpec.Builder builder)
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
