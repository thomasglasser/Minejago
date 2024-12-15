package dev.thomasglasser.minejago.data.loot;

import static dev.thomasglasser.minejago.world.level.storage.loot.MinejagoArchaeologyLootKeys.CAVE_OF_DESPAIR;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public record MinejagoArchaeologyLoot(HolderLookup.Provider provider) implements LootTableSubProvider {
    public static final LootTable.Builder CAVE_OF_DESPAIR_TABLE = LootTable.lootTable().withPool(
            LootPool.lootPool()
                    .add(LootItem.lootTableItem(MinejagoItems.PEAKS_POTTERY_SHERD.get()))
                    .add(LootItem.lootTableItem(MinejagoItems.TERRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get()))
                    .add(LootItem.lootTableItem(Items.TERRACOTTA))
                    .add(LootItem.lootTableItem(Items.LIGHT_GRAY_TERRACOTTA))
                    .add(LootItem.lootTableItem(Items.CLAY_BALL))
                    .add(LootItem.lootTableItem(Items.EMERALD)));

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(CAVE_OF_DESPAIR, CAVE_OF_DESPAIR_TABLE);
    }
}
