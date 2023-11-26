package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.registration.BlockRegistryObject;
import dev.thomasglasser.minejago.world.level.block.LeavesSet;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.WoodSet;
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
        for (BlockRegistryObject<Block> pot : MinejagoBlocks.TEAPOTS.values())
            add(pot.get(), createTeapotBlock(pot.get()));
        add(MinejagoBlocks.JASPOT.get(), createTeapotBlock(MinejagoBlocks.JASPOT.get()));
        add(MinejagoBlocks.FLAME_TEAPOT.get(), createTeapotBlock(MinejagoBlocks.FLAME_TEAPOT.get()));

        dropSelf(MinejagoBlocks.GOLD_DISC.get());
        dropSelf(MinejagoBlocks.TOP_POST.get());

        add(MinejagoBlocks.SUSPICIOUS_RED_SAND.get(), noDrop());

        add(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), createSilkTouchOnlyTable(MinejagoBlocks.CHISELED_SCROLL_SHELF.get()));

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);
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

    private void woodSet(WoodSet set)
    {
        dropSelf(set.planks().get());
        dropSelf(set.log().get());
        dropSelf(set.strippedLog().get());
        dropSelf(set.wood().get());
        dropSelf(set.strippedWood().get());

    }

    private void leavesSet(LeavesSet set)
    {
        add(set.leaves().get(), createLeavesDrops(set.leaves().get(), set.sapling().get(), NORMAL_LEAVES_SAPLING_CHANCES));

        dropSelf(set.sapling().get());

        dropPottedContents(set.pottedSapling().get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        ArrayList<Block> list = new ArrayList<>();
        MinejagoBlocks.BLOCKS.getEntries().forEach(ro -> list.add(ro.get()));
        return list;
    }
}
