package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.function.Supplier;

public class MinejagoBannerPatterns
{
    public static final RegistrationProvider<BannerPattern> BANNER_PATTERNS = RegistrationProvider.get(BuiltInRegistries.BANNER_PATTERN, Minejago.MOD_ID);

    public static final RegistryObject<BannerPattern> FOUR_WEAPONS_LEFT = register("four_weapons_left", () -> new BannerPattern("four_weapons_left"));
    public static final RegistryObject<BannerPattern> FOUR_WEAPONS_RIGHT = register("four_weapons_right", () -> new BannerPattern("four_weapons_right"));
    public static final RegistryObject<BannerPattern> EDGE_LINES = register("edge_lines", () -> new BannerPattern("edge_lines"));

    private static RegistryObject<BannerPattern> register(String name, Supplier<BannerPattern> bannerPattern)
    {
        return BANNER_PATTERNS.register(name, bannerPattern);
    }

    public static void init() {}
}
