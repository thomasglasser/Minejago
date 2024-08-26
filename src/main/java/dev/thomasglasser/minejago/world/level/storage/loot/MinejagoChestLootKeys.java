package dev.thomasglasser.minejago.world.level.storage.loot;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class MinejagoChestLootKeys {
    public static final ResourceKey<LootTable> FOUR_WEAPONS = modLoc("four_weapons");
    public static final ResourceKey<LootTable> MONASTERY_OF_SPINJITZU_WUS_ROOM = modLoc("monastery_of_spinjitzu_wus_room");
    public static final ResourceKey<LootTable> MONASTERY_OF_SPINJITZU_KITCHEN = modLoc("monastery_of_spinjitzu_kitchen");

    private static ResourceKey<LootTable> modLoc(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Minejago.modLoc("chests/" + name));
    }
}
