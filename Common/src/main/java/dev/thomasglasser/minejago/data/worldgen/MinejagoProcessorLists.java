package dev.thomasglasser.minejago.data.worldgen;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.loot.MinejagoArchaeologyLootKeys;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.CappedProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

public class MinejagoProcessorLists
{
    public static final ResourceKey<StructureProcessorList> CAVE_OF_DESPAIR = register("cave_of_despair");


    private static ResourceKey<StructureProcessorList> register(String path)
    {
        return ResourceKey.create(Registries.PROCESSOR_LIST, Minejago.modLoc(path));
    }

    public static void bootstrap(BootstrapContext<StructureProcessorList> bootstapContext)
    {
        bootstapContext.register(CAVE_OF_DESPAIR, new StructureProcessorList(List.of(
                archyRuleProcessor(Blocks.RED_SAND, MinejagoBlocks.SUSPICIOUS_RED_SAND.get(), MinejagoArchaeologyLootKeys.CAVE_OF_DESPAIR)
        )));
    }

    private static StructureProcessor archyRuleProcessor(Block input, Block output, ResourceKey<LootTable> lootTable) {
        return new CappedProcessor(new RuleProcessor(List.of(new ProcessorRule(new BlockMatchTest(input), AlwaysTrueTest.INSTANCE, PosAlwaysTrueTest.INSTANCE, output.defaultBlockState(), new AppendLoot(lootTable)))), UniformInt.of(5, 15));
    }
}
