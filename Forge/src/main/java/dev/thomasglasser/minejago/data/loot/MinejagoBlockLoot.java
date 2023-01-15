package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class MinejagoBlockLoot implements LootTableSubProvider
{
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(MinejagoBlocks.TEAPOT.get().getLootTable(), createTeapotBlock(MinejagoBlocks.TEAPOT.get()));
    }

    protected LootTable.Builder createTeapotBlock(Block block)
    {
        return LootTable.lootTable()
                .withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block)
                                .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                        .copy("Boiling","BlockEntityTag.Boiling")
                                        .copy("Done", "BlockEntityTag.Done")
                                        .copy("Cups","BlockEntityTag.Cups")
                                        .copy("LootTable", "BlockEntityTag.LootTable")
                                        .copy("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                        .copy("HasLeaves", "BlockEntityTag.HasLeaves")
                                        .copy("Temperature", "BlockEntityTag.Temperature")
                                        .copy("Potion", "BlockEntityTag.Potion"))
                                .apply(SetContainerContents.setContents(MinejagoBlockEntityTypes.TEAPOT.get()).withEntry(DynamicLoot.dynamicEntry(TeapotBlock.CONTENTS))))
        );
    }
}
