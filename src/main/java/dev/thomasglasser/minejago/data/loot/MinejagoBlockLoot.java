package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.tommylib.api.data.loot.ExtendedBlockLootSubProvider;
import dev.thomasglasser.tommylib.api.registration.DeferredBlock;
import java.util.Set;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class MinejagoBlockLoot extends ExtendedBlockLootSubProvider {
    public MinejagoBlockLoot(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider, MinejagoBlocks.BLOCKS);
    }

    @Override
    public void generate() {
        add(MinejagoBlocks.TEAPOT.get(), createContainerDrop(MinejagoBlocks.TEAPOT.get()));
        for (DeferredBlock<TeapotBlock> pot : MinejagoBlocks.TEAPOTS.values())
            add(pot.get(), createContainerDrop(pot.get()));
        add(MinejagoBlocks.JASPOT.get(), createContainerDrop(MinejagoBlocks.JASPOT.get()));

        dropSelf(MinejagoBlocks.GOLD_DISC.get());
        dropSelf(MinejagoBlocks.TOP_POST.get());
        dropSelf(MinejagoBlocks.DRAGON_BUTTON.get());

        add(MinejagoBlocks.SUSPICIOUS_RED_SAND.get(), noDrop());

        add(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), createSilkTouchOnlyTable(MinejagoBlocks.CHISELED_SCROLL_SHELF.get()));
        add(MinejagoBlocks.SCROLL_SHELF.get(), block -> createSingleItemTableWithSilkTouch(block, MinejagoItems.SCROLL.get(), ConstantValue.exactly(3.0F)));

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);
    }

    protected LootTable.Builder createContainerDrop(Block block) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(block)
                                                .apply(
                                                        CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                                .include(DataComponents.CONTAINER))));
    }
}
