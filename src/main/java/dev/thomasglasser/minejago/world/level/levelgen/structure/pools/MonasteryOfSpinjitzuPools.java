package dev.thomasglasser.minejago.world.level.levelgen.structure.pools;

import static net.minecraft.data.worldgen.Pools.EMPTY;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class MonasteryOfSpinjitzuPools {
    public static final ResourceKey<StructureTemplatePool> START = MinejagoPools.createKey("monastery_of_spinjitzu/base");
    public static final ResourceKey<StructureTemplatePool> GROUND = MinejagoPools.createKey("monastery_of_spinjitzu/ground");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> bootstrapContext) {
        HolderGetter<StructureTemplatePool> holderGetter = bootstrapContext.lookup(Registries.TEMPLATE_POOL);

        Holder<StructureTemplatePool> holder = holderGetter.getOrThrow(EMPTY);

        bootstrapContext.register(
                START,
                new StructureTemplatePool(
                        holder, ImmutableList.of(Pair.of(StructurePoolElement.single(START.location().toString()), 1)), StructureTemplatePool.Projection.RIGID));
        bootstrapContext.register(
                GROUND,
                new StructureTemplatePool(
                        holder, ImmutableList.of(Pair.of(StructurePoolElement.single(GROUND.location().toString()), 1)), StructureTemplatePool.Projection.RIGID));
    }
}
