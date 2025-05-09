package dev.thomasglasser.minejago.server;

import net.neoforged.neoforge.common.ModConfigSpec;

public class MinejagoServerConfig {
    private static final MinejagoServerConfig INSTANCE = new MinejagoServerConfig();

    public final ModConfigSpec configSpec;

    // Features
    public static final String FEATURES = "features";
    public final ModConfigSpec.BooleanValue enableTech;
    public final ModConfigSpec.BooleanValue enableSkulkinRaids;

    // Elements
    public static final String ELEMENTS = "elements";
    public final ModConfigSpec.BooleanValue allowChoose;
    public final ModConfigSpec.BooleanValue allowChange;
    public final ModConfigSpec.BooleanValue drainPool;
    public final ModConfigSpec.BooleanValue enableNoElement;

    // Spinjitzu
    public static final String SPINJITZU = "spinjitzu";
    public final ModConfigSpec.BooleanValue requireCourseCompletion;
    public final ModConfigSpec.IntValue courseTimeLimit;
    public final ModConfigSpec.IntValue courseRadius;
    public final ModConfigSpec.DoubleValue courseSpeed;

    // Tornado Of Creation
    public static final String TORNADO_OF_CREATION = "tornado_of_creation";
    public final ModConfigSpec.IntValue resourcesAbsorbedPerSecond;
    public final ModConfigSpec.IntValue timeout;
    public final ModConfigSpec.IntValue resourceAbsorbDistance;

    public MinejagoServerConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push(FEATURES);
        enableTech = builder
                .define("enable_tech", true);
        enableSkulkinRaids = builder
                .define("enable_skulkin_raids", true);
        builder.pop();

        builder.push(ELEMENTS);
        allowChoose = builder
                .define("allow_choose", false);
        allowChange = builder
                .define("allow_change", false);
        drainPool = builder
                .define("drain_pool", true);
        enableNoElement = builder
                .define("enable_no_element", true);
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

        builder.push(TORNADO_OF_CREATION);
        resourcesAbsorbedPerSecond = builder
                .defineInRange("resources_absorbed_per_second", 1, 1, 16);
        timeout = builder
                .defineInRange("timeout", 60, 10, 600);
        resourceAbsorbDistance = builder
                .defineInRange("resource_absorb_distance", 16, 4, 32);

        configSpec = builder.build();
    }

    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    public static MinejagoServerConfig get() {
        return INSTANCE;
    }
}
