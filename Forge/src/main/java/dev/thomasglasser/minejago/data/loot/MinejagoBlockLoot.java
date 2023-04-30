package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.registration.BlockRegistryObject;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
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

import java.util.ArrayList;
import java.util.Set;

public class MinejagoBlockLoot extends BlockLootSubProvider {
    public MinejagoBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        add(MinejagoBlocks.TEAPOT.get(), createTeapotBlock(MinejagoBlocks.TEAPOT.get()));
        add(MinejagoBlocks.JASPOT.get(), createTeapotBlock(MinejagoBlocks.JASPOT.get()));

        for (BlockRegistryObject<Block> pot : MinejagoBlocks.TEAPOTS.values())
            add(pot.get(), createTeapotBlock(pot.get()));

        dropSelf(MinejagoBlocks.GOLD_DISC.get());
        dropSelf(MinejagoBlocks.TOP_POST.get());
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

    @Override
    protected Iterable<Block> getKnownBlocks() {
        ArrayList<Block> list = new ArrayList<>();
        MinejagoBlocks.BLOCKS.getEntries().forEach(ro -> list.add(ro.get()));
        return list;
    }
}
