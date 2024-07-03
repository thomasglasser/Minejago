package dev.thomasglasser.minejago.server;

import net.neoforged.neoforge.common.ModConfigSpec;

public class MinejagoServerConfig {
    public static final MinejagoServerConfig INSTANCE = new MinejagoServerConfig();

    public final ModConfigSpec configSpec;

    // Features
    public final ModConfigSpec.BooleanValue enableTech;
    public final ModConfigSpec.BooleanValue enableSkulkinRaids;

    // Powers
    public final ModConfigSpec.BooleanValue allowChoose;
    public final ModConfigSpec.BooleanValue allowChange;
    public final ModConfigSpec.BooleanValue drainPool;
    public final ModConfigSpec.BooleanValue enableNoPower;

    // Golden Weapons
    public final ModConfigSpec.BooleanValue requireCompatiblePower;
    public final ModConfigSpec.BooleanValue enableMalfunction;

    public MinejagoServerConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.comment("Optional features that enhance the mod, but may not match the desired experience of some players");
        builder.push("features");
        enableTech = builder
                .comment("Enable the technology of the mod, such as vehicles and computers")
                .define("enable_tech", true);
        enableSkulkinRaids = builder
                .comment("Enable Skulkin Raids on Four Weapons structures")
                .define("enable_skulkin_raids", true);
        builder.pop();

        builder.comment("Settings for powers (elemental or otherwise)");
        builder.push("powers");
        allowChoose = builder
                .comment("Allow players to choose the power given to them by interacting with Master Wu")
                .define("allow_choose", false);
        allowChange = builder
                .comment("Allow players to get a new power by interacting with Master Wu again")
                .define("allow_change", false);
        drainPool = builder
                .comment("Remove a power from the option list once given and reset when all powers have been given")
                .define("drain_pool", true);
        enableNoPower = builder
                .comment("Enable players to receive no power from Master Wu")
                .define("enable_no_power", true);
        builder.pop();

        builder.comment("Settings for the four Golden Weapons");
        builder.push("golden_weapons");
        requireCompatiblePower = builder
                .comment("Require users to have a compatible power")
                .define("require_compatible_power", true);
        enableMalfunction = builder
                .comment("Enable an abnormal reaction when handled by someone without a compatible power")
                .define("enable_malfunction", true);
        builder.pop();

        configSpec = builder.build();
    }

    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }
}
