package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import java.util.function.BiConsumer;

public class MinejagoChestLoot extends ChestLoot
{
    private static final LootTable.Builder FOUR_WEAPONS = LootTable.lootTable().withPool(
            LootPool.lootPool()
            .add(LootItem.lootTableItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get())));

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(modLoc("four_weapons"), FOUR_WEAPONS);
    }

    private ResourceLocation modLoc(String name)
    {
        return new ResourceLocation(Minejago.MOD_ID, "chests/" + name);
    }
}
