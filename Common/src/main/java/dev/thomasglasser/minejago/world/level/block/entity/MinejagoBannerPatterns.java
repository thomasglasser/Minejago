package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.function.Supplier;

public class MinejagoBannerPatterns
{

    public static final Supplier<BannerPattern> FOUR_WEAPONS_LEFT = register("four_weapons_left", () -> new BannerPattern("four_weapons_left"));
    public static final Supplier<BannerPattern> FOUR_WEAPONS_RIGHT = register("four_weapons_right", () -> new BannerPattern("four_weapons_right"));
    public static final Supplier<BannerPattern> EDGE_LINES = register("edge_lines", () -> new BannerPattern("edge_lines"));

    private static Supplier<BannerPattern> register(String name, Supplier<BannerPattern> bannerPattern)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.BANNER_PATTERN, name, bannerPattern);
    }

    public static void init() {}
}
