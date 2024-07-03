package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MinejagoClientConfig {
    public static final MinejagoClientConfig INSTANCE = new MinejagoClientConfig();

    public final ModConfigSpec configSpec;

    // Cosmetics
    public final ModConfigSpec.BooleanValue displaySnapshotTesterCosmetic;
    public final ModConfigSpec.EnumValue<SnapshotTesterCosmeticOptions> snapshotTesterCosmeticChoice;
    public final ModConfigSpec.BooleanValue displayDevTeamCosmetic;
    public final ModConfigSpec.BooleanValue displayOgDevTeamCosmetic;

    // Focus Bar
    public final ModConfigSpec.IntValue xOffset;
    public final ModConfigSpec.IntValue yOffset;

    public MinejagoClientConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Settings for player cosmetics");
        builder.push("cosmetics");
        displaySnapshotTesterCosmetic = builder
                .comment("Display your preferred Snapshot Tester Cosmetic (if eligible)")
                .define("display_snapshot_tester_cosmetic", true);
        snapshotTesterCosmeticChoice = builder
                .comment("The Snapshot Tester Cosmetic to be displayed (if eligible)")
                .defineEnum("snapshot_tester_cosmetic_choice", SnapshotTesterCosmeticOptions.BAMBOO_HAT);
        displayDevTeamCosmetic = builder
                .comment("Display the Dev Team cosmetic (if eligible)")
                .define("display_dev_team_cosmetic", true);
        displayOgDevTeamCosmetic = builder
                .comment("Display the OG Dev Team cosmetic (if eligible)")
                .define("display_og_dev_team_cosmetic", true);
        builder.pop();

        builder.comment("Settings for focus bar");
        builder.push("focus_bar");
        xOffset = builder
                .comment("Horizontal pixels off from the normal position")
                .defineInRange("x_offset", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        yOffset = builder
                .comment("Vertical pixels off from the normal position")
                .defineInRange("y_offset", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        builder.pop();

        configSpec = builder.build();
    }

    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }
}
