package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import net.minecraft.data.loot.BlockLoot;
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

public class MinejagoBlockLoot extends BlockLoot
{
    public static final LootTable.Builder TEAPOT = createTeapotBlock(MinejagoBlocks.TEAPOT.get());

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(modLoc("teapot"), TEAPOT);
    }

    protected static LootTable.Builder createTeapotBlock(Block block)
    {
        return LootTable.lootTable().withPool(applyExplosionCondition(block,
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
                                        .copy("Temperature", "BlockEntityTag.Temperature"))
                                .apply(SetContainerContents.setContents(MinejagoBlockEntityTypes.TEAPOT.get()).withEntry(DynamicLoot.dynamicEntry(TeapotBlock.CONTENTS))))
        ));
    }
    
    private ResourceLocation modLoc(String name)
{
    return new ResourceLocation(Minejago.MOD_ID, "blocks/" + name);
}
}
