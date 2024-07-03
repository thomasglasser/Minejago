package dev.thomasglasser.minejago.data.loot;

import static dev.thomasglasser.minejago.data.loot.MinejagoArchaeologyLootKeys.CAVE_OF_DESPAIR;

import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
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
                    .add(LootItem.lootTableItem(MinejagoItems.POTTERY_SHERD_PEAKS.get()))
                    .add(LootItem.lootTableItem(Items.TERRACOTTA))
                    .add(LootItem.lootTableItem(Items.LIGHT_GRAY_TERRACOTTA))
                    .add(LootItem.lootTableItem(Items.CLAY_BALL))
                    .add(LootItem.lootTableItem(Items.EMERALD))
                    .add(LootItem.lootTableItem(MinejagoItems.BONE_KNIFE.get()))
                    .add(LootItem.lootTableItem(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.STRENGTH).get()))
                    .add(LootItem.lootTableItem(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED).get()))
                    .add(LootItem.lootTableItem(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.KNIFE).get()))
                    .add(LootItem.lootTableItem(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.BOW).get())));

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(CAVE_OF_DESPAIR, CAVE_OF_DESPAIR_TABLE);
    }
}
