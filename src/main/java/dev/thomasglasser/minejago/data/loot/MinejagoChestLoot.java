package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.storage.loot.MinejagoChestLootKeys;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public record MinejagoChestLoot(HolderLookup.Provider provider) implements LootTableSubProvider {
    public static final LootTable.Builder FOUR_WEAPONS = LootTable.lootTable()
            .withPool(LootPool.lootPool()
                    .setRolls(UniformGenerator.between(0.0F, 1.0F))
                    .add(LootItem.lootTableItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get())))
            .withPool(LootPool.lootPool()
                    .setRolls(UniformGenerator.between(0.0F, 1.0F))
                    .add(LootItem.lootTableItem(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get())));

    public static final LootTable.Builder MONASTERY_OF_SPINJITZU = LootTable.lootTable()
            .withPool(LootPool.lootPool()
                    .setRolls(UniformGenerator.between(0.0F, 1.0F))
                    .add(LootItem.lootTableItem(MinejagoItems.NINJA_BANNER_PATTERN.get())))
            .withPool(LootPool.lootPool()
                    .setRolls(UniformGenerator.between(0.0F, 1.0F))
                    .add(LootItem.lootTableItem(MinejagoItems.LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE.get())));

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> p_249643_) {
        p_249643_.accept(MinejagoChestLootKeys.FOUR_WEAPONS, FOUR_WEAPONS);
        p_249643_.accept(MinejagoChestLootKeys.MONASTERY_OF_SPINJITZU, MONASTERY_OF_SPINJITZU);
    }
}
