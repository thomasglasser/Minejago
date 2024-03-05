package dev.thomasglasser.minejago.server;

import eu.midnightdust.lib.config.MidnightConfig;

public class MinejagoServerConfig extends MidnightConfig
{
    // Feature Toggles
    @Comment(category = "features", centered = true) public static final String features_comment = "Optional features that enhance the mod, ";
    @Comment(category = "features", centered = true) public static final String features_comment_continued = "but may not match the desired experience of some players";
    @Comment(category = "features") public static final String enable_tech_comment = "Enable the technology of the mod, such as vehicles and computers";
    @Entry(category = "features")
    public static boolean enableTech = true;
    @Comment(category = "features") public static final String enable_skulkin_raids_comment = "Enable Skulkin Raids on Four Weapons structures";
    @Entry(category = "features")
    public static boolean enableSkulkinRaids = true;

    // Powers
    @Comment(category = "powers", centered = true) public static final String powers_comment = "Settings for powers (elemental or otherwise)";
    @Comment(category = "powers") public static final String allow_choose_comment = "Allow players to choose the power given to them by interacting with Master Wu";
    @Entry(category = "powers")
    public static boolean allowChoose = false;
    @Comment(category = "powers") public static final String allow_change_comment = "Allow players to get a new power by interacting with Master Wu again";
    @Entry(category = "powers")
    public static boolean allowChange = false;
    @Comment(category = "powers") public static final String drain_pool_comment = "Remove a power from the option list once given and reset when all powers have been given";
    @Entry(category = "powers")
    public static boolean drainPool = true;
    @Comment(category = "powers") public static final String enable_no_power_comment = "Enable players to receive no power from Master Wu";
    @Entry(category = "powers")
    public static boolean enableNoPower = true;

    // Golden Weapons
    @Comment(category = "golden_weapons", centered = true) public static final String golden_weapons_comment = "Settings for the four Golden Weapons";
    @Comment(category = "golden_weapons") public static final String require_compatible_power_comment = "Require users to have a compatible power";
    @Entry(category = "golden_weapons")
    public static boolean requireCompatiblePower = true;
    @Comment(category = "golden_weapons") public static final String enable_malfunction_comment = "Enable an abnormal reaction when handled by someone without a compatible power";
    @Entry(category = "golden_weapons")
    public static boolean enableMalfunction = true;
}
