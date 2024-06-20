package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import java.util.function.BiConsumer;

public record MinejagoChestLoot(HolderLookup.Provider provider) implements LootTableSubProvider
{
    public static final LootTable.Builder FOUR_WEAPONS = LootTable.lootTable().withPool(
            LootPool.lootPool()
                    .add(LootItem.lootTableItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get()))
                    .add(LootItem.lootTableItem(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
    );

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> p_249643_)
    {
        p_249643_.accept(MinejagoChestLootKeys.FOUR_WEAPONS, FOUR_WEAPONS);
    }
}
