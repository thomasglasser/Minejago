package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;
import eu.midnightdust.lib.config.MidnightConfig;

public class MinejagoClientConfig extends MidnightConfig
{
    // Player Cosmetics
    @Comment(category = "cosmetics", centered = true) public static final String player_cosmetics_comment = "Settings for player cosmetics";
    @Comment(category = "cosmetics") public static final String display_snapshot_tester_cosmetic_comment = "Display your preferred Snapshot Tester Cosmetic (if eligible)";
    @Entry(category = "cosmetics")
    public static boolean displaySnapshotTesterCosmetic = true;
    @Comment(category = "cosmetics") public static final String snapshot_tester_cosmetic_choice_comment = "The Snapshot Tester Cosmetic to be displayed (if eligible)";
    @Entry(category = "cosmetics")
    public static SnapshotTesterCosmeticOptions snapshotTesterCosmeticChoice = SnapshotTesterCosmeticOptions.BAMBOO_HAT;
//    @Comment(category = "cosmetics") public static final String DISPLAY_DEV_TEAM_LAYER_COMMENT = "Display the Dev Team cosmetic (if eligible)";
//    @Entry(category = "cosmetics", name = "Display Dev Team Cosmetic")
//    public static boolean displayDevTeamCosmetic = true;
    @Comment(category = "cosmetics") public static final String display_og_dev_team_cosmetic_comment = "Display the OG Dev Team cosmetic (if eligible)";
    @Entry(category = "cosmetics")
    public static boolean displayOgDevTeamCosmetic = true;

    // Focus Bar
    @Comment(category = "focus_bar", centered = true) public static final String focus_bar_comment = "Settings for focus bar";
    @Comment(category = "focus_bar") public static final String x_offset_comment = "Horizontal pixels off from the normal position";
    @Entry(category = "focus_bar")
    public static int xOffset;
    @Comment(category = "focus_bar") public static final String y_offset_comment = "Vertical pixels off from the normal position";
    @Entry(category = "focus_bar")
    public static int yOffset;
}
