package dev.thomasglasser.minejago.server;

import net.neoforged.neoforge.common.ModConfigSpec;

public class MinejagoServerConfig {
    private static final MinejagoServerConfig INSTANCE = new MinejagoServerConfig();

    public final ModConfigSpec configSpec;

    // Features
    public static final String FEATURES = "features";
    public final ModConfigSpec.BooleanValue enableTech;
    public final ModConfigSpec.BooleanValue enableSkulkinRaids;

    // Powers
    public static final String POWERS = "powers";
    public final ModConfigSpec.BooleanValue allowChoose;
    public final ModConfigSpec.BooleanValue allowChange;
    public final ModConfigSpec.BooleanValue drainPool;
    public final ModConfigSpec.BooleanValue enableNoPower;

    // Spinjitzu
    public static final String SPINJITZU = "spinjitzu";
    public final ModConfigSpec.BooleanValue requireCourseCompletion;
    public final ModConfigSpec.IntValue courseTimeLimit;
    public final ModConfigSpec.IntValue courseRadius;
    public final ModConfigSpec.DoubleValue courseSpeed;

    public MinejagoServerConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push(FEATURES);
        enableTech = builder
                .define("enable_tech", true);
        enableSkulkinRaids = builder
                .define("enable_skulkin_raids", true);
        builder.pop();

        builder.push(POWERS);
        allowChoose = builder
                .define("allow_choose", false);
        allowChange = builder
                .define("allow_change", false);
        drainPool = builder
                .define("drain_pool", true);
        enableNoPower = builder
                .define("enable_no_power", true);
        builder.pop();

        builder.push(SPINJITZU);
        requireCourseCompletion = builder
                .define("require_course_completion", true);
        courseTimeLimit = builder
                .defineInRange("course_time_limit", 30, 5, 300);
        courseRadius = builder
                .defineInRange("course_radius", 64, 1, 128);
        courseSpeed = builder
                .defineInRange("course_speed", 0.5, 0.1, 1);
        builder.pop();

        configSpec = builder.build();
    }

    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    public static MinejagoServerConfig get() {
        return INSTANCE;
    }
}
