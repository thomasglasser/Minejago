package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MinejagoClientConfig {
    private static final MinejagoClientConfig INSTANCE = new MinejagoClientConfig();

    public final ModConfigSpec configSpec;

    // Cosmetics
    public static final String COSMETICS = "cosmetics";
    public final ModConfigSpec.BooleanValue displaySnapshotTesterCosmetic;
    public final ModConfigSpec.EnumValue<SnapshotTesterCosmeticOptions> snapshotTesterCosmeticChoice;
    public final ModConfigSpec.BooleanValue displayDevTeamCosmetic;
    public final ModConfigSpec.BooleanValue displayLegacyDevTeamCosmetic;

    // Focus Bar
    public static final String FOCUS_BAR = "focus_bar";
    public final ModConfigSpec.ConfigValue<Integer> xOffset;
    public final ModConfigSpec.ConfigValue<Integer> yOffset;

    public MinejagoClientConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push(COSMETICS);
        displaySnapshotTesterCosmetic = builder
                .define("display_snapshot_tester_cosmetic", true);
        snapshotTesterCosmeticChoice = builder
                .defineEnum("snapshot_tester_cosmetic_choice", SnapshotTesterCosmeticOptions.BAMBOO_HAT);
        displayDevTeamCosmetic = builder
                .define("display_dev_team_cosmetic", true);
        displayLegacyDevTeamCosmetic = builder
                .define("display_legacy_dev_team_cosmetic", true);
        builder.pop();

        builder.push(FOCUS_BAR);
        xOffset = builder
                .define("x_offset", 0);
        yOffset = builder
                .define("y_offset", 0);
        builder.pop();

        configSpec = builder.build();
    }

    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    public static MinejagoClientConfig get() {
        return INSTANCE;
    }
}
