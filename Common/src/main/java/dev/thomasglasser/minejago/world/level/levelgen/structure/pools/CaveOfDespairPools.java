package dev.thomasglasser.minejago.world.level.levelgen.structure.pools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.data.worldgen.MinejagoProcessorLists;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import static dev.thomasglasser.minejago.Minejago.modLoc;
import static net.minecraft.data.worldgen.Pools.EMPTY;

public class CaveOfDespairPools
{
    public static final ResourceKey<StructureTemplatePool> START = MinejagoPools.createKey("cave_of_despair/base");

    public static void bootstrap(BootstapContext<StructureTemplatePool> bootstapContext) {
        HolderGetter<StructureTemplatePool> templatePools = bootstapContext.lookup(Registries.TEMPLATE_POOL);
        HolderGetter<StructureProcessorList> processorLists = bootstapContext.lookup(Registries.PROCESSOR_LIST);

        Holder<StructureTemplatePool> empty = templatePools.getOrThrow(EMPTY);
        Holder<StructureProcessorList> susPlacer = processorLists.getOrThrow(MinejagoProcessorLists.CAVE_OF_DESPAIR);

        bootstapContext.register(
                START,
                new StructureTemplatePool(
                        empty, ImmutableList.of(Pair.of(MinejagoPools.singleElement(modLoc("cave_of_despair/base"), susPlacer), 1)), StructureTemplatePool.Projection.RIGID
                )
        );
    }
}