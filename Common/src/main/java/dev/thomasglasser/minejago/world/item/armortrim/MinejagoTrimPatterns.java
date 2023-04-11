package dev.thomasglasser.minejago.world.item.armortrim;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.armortrim.TrimPattern;

public class MinejagoTrimPatterns
{
    public static final ResourceKey<TrimPattern> FOUR_WEAPONS = registryKey("four_weapons");

    public static void bootstrap(BootstapContext<TrimPattern> context) {
        context.register(FOUR_WEAPONS, new TrimPattern(FOUR_WEAPONS.location(), MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get().builtInRegistryHolder(), Component.translatable(Util.makeDescriptionId("trim_pattern", FOUR_WEAPONS.location()))));
    }

    private static ResourceKey<TrimPattern> registryKey(String path) {
        return ResourceKey.create(Registries.TRIM_PATTERN, Minejago.modLoc(path));
    }
}
