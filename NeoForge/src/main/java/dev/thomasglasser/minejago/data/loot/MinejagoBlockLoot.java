package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.tommylib.api.data.loot.ExtendedBlockLootSubProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.ContainerComponentManipulators;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;

public class MinejagoBlockLoot extends ExtendedBlockLootSubProvider
{
    public MinejagoBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        add(MinejagoBlocks.TEAPOT.get(), createTeapotBlock(MinejagoBlocks.TEAPOT.get()));
        for (RegistryObject<TeapotBlock> pot : MinejagoBlocks.TEAPOTS.values())
            add(pot.get(), createTeapotBlock(pot.get()));
        add(MinejagoBlocks.JASPOT.get(), createTeapotBlock(MinejagoBlocks.JASPOT.get()));
        add(MinejagoBlocks.FLAME_TEAPOT.get(), createTeapotBlock(MinejagoBlocks.FLAME_TEAPOT.get()));

        dropSelf(MinejagoBlocks.GOLD_DISC.get());
        dropSelf(MinejagoBlocks.TOP_POST.get());

        add(MinejagoBlocks.SUSPICIOUS_RED_SAND.get(), noDrop());

        add(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), createSilkTouchOnlyTable(MinejagoBlocks.CHISELED_SCROLL_SHELF.get()));

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET.get());
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET.get());
    }

    protected LootTable.Builder createTeapotBlock(Block block)
    {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(block)
                                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                        // TODO: Ensure this info is copied correctly
                                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY))
                                        .apply(SetContainerContents.setContents(ContainerComponentManipulators.CONTAINER).withEntry(DynamicLoot.dynamicEntry(TeapotBlock.CONTENTS))))
                );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
	    return MinejagoBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}
