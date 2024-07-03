package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class MinejagoArchaeologyLootKeys {
    public static final ResourceKey<LootTable> CAVE_OF_DESPAIR = modLoc("cave_of_despair");

    private static ResourceKey<LootTable> modLoc(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Minejago.modLoc("archaeology/" + name));
    }
}
