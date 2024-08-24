package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class MinejagoBannerPatterns {
    public static final ResourceKey<BannerPattern> FOUR_WEAPONS_LEFT = create("four_weapons_left");
    public static final ResourceKey<BannerPattern> FOUR_WEAPONS_RIGHT = create("four_weapons_right");
    public static final ResourceKey<BannerPattern> EDGE_LINES = create("edge_lines");
    public static final ResourceKey<BannerPattern> NINJA = create("ninja");

    private static ResourceKey<BannerPattern> create(String name) {
        return ResourceKey.create(Registries.BANNER_PATTERN, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<BannerPattern> context) {
        register(context, FOUR_WEAPONS_LEFT);
        register(context, FOUR_WEAPONS_RIGHT);
        register(context, EDGE_LINES);
        register(context, NINJA);
    }

    public static void register(BootstrapContext<BannerPattern> bootstrapContext, ResourceKey<BannerPattern> resourceKey) {
        bootstrapContext.register(resourceKey, new BannerPattern(resourceKey.location(), "block.minecraft.banner." + resourceKey.location().toShortLanguageKey()));
    }
}
