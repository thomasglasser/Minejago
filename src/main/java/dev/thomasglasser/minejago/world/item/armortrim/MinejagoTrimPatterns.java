package dev.thomasglasser.minejago.world.item.armortrim;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.armortrim.TrimPattern;

public class MinejagoTrimPatterns {
    public static final ResourceKey<TrimPattern> FOUR_WEAPONS = registryKey("four_weapons");
    public static final ResourceKey<TrimPattern> TERRAIN = registryKey("terrain");
    public static final ResourceKey<TrimPattern> LOTUS = registryKey("lotus");

    private static ResourceKey<TrimPattern> registryKey(String path) {
        return ResourceKey.create(Registries.TRIM_PATTERN, Minejago.modLoc(path));
    }
}
