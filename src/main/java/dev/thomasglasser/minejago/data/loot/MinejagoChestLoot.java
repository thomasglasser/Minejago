package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.storage.loot.MinejagoChestLootKeys;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public record MinejagoChestLoot(HolderLookup.Provider provider) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> p_249643_) {
        p_249643_.accept(MinejagoChestLootKeys.FOUR_WEAPONS, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get())))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get()))));
        p_249643_.accept(MinejagoChestLootKeys.MONASTERY_OF_SPINJITZU_WUS_ROOM, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(MinejagoItems.NINJA_BANNER_PATTERN.get())))
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0.0F, 1.0F))
                        .add(LootItem.lootTableItem(MinejagoItems.LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE.get()))));
        p_249643_.accept(MinejagoChestLootKeys.MONASTERY_OF_SPINJITZU_KITCHEN, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.BREAD))
                        .add(LootItem.lootTableItem(Items.BEEF))
                        .add(LootItem.lootTableItem(Items.CHICKEN))
                        .add(LootItem.lootTableItem(Items.JUNGLE_LEAVES))
                        .add(LootItem.lootTableItem(Items.OAK_LEAVES))
                        .add(LootItem.lootTableItem(Items.CARROT))
                        .add(LootItem.lootTableItem(Items.POTATO))
                        .add(LootItem.lootTableItem(Items.APPLE))));
    }
}
