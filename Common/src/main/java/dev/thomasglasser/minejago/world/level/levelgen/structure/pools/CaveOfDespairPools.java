package dev.thomasglasser.minejago.world.level.levelgen.structure.pools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;

import java.util.List;

import static dev.thomasglasser.minejago.Minejago.modLoc;
import static net.minecraft.data.worldgen.Pools.EMPTY;

public class CaveOfDespairPools
{
    public static final StructureProcessor RED_SAND_BLOCK_PROCESSOR = archyRuleProcessor(Blocks.RED_SAND, MinejagoBlocks.SUSPICIOUS_RED_SAND.get(), modLoc("cave_of_despair"));

    public static final ResourceKey<StructureTemplatePool> START = MinejagoPools.createKey("cave_of_despair/base");

    public static void bootstrap(BootstapContext<StructureTemplatePool> bootstapContext) {
        HolderGetter<StructureTemplatePool> holderGetter = bootstapContext.lookup(Registries.TEMPLATE_POOL);

        Holder<StructureTemplatePool> holder = holderGetter.getOrThrow(EMPTY);

        bootstapContext.register(
                START,
                new StructureTemplatePool(
                        holder, ImmutableList.of(Pair.of(MinejagoPools.singleElement(modLoc("cave_of_despair/base")), 1)), StructureTemplatePool.Projection.RIGID
                )
        );
    }

    private static StructureProcessor archyRuleProcessor(Block input, Block output, ResourceLocation lootTable) {
        return new CappedProcessor(new RuleProcessor(List.of(new ProcessorRule(new BlockMatchTest(input), AlwaysTrueTest.INSTANCE, PosAlwaysTrueTest.INSTANCE, output.defaultBlockState(), new AppendLoot(lootTable)))), ConstantInt.of(5));
    }
}