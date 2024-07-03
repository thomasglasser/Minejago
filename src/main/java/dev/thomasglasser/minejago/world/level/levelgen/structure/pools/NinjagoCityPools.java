package dev.thomasglasser.minejago.world.level.levelgen.structure.pools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class NinjagoCityPools {
    public static final ResourceKey<StructureTemplatePool> BILLBOARDS = createKey("ninjago_city/billboards");
    public static final ResourceKey<StructureTemplatePool> BUILDINGS = createKey("ninjago_city/buildings");

    private static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<StructureTemplatePool> bootstapContext) {
        HolderGetter<StructureTemplatePool> templatePools = bootstapContext.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> empty = templatePools.getOrThrow(Pools.EMPTY);
        bootstapContext.register(BILLBOARDS, new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(StructurePoolElement.single("minejago:ninjago_city/billboards/team_billboard"), 4),
                Pair.of(StructurePoolElement.single("minejago:ninjago_city/billboards/jay_billboard"), 3)), StructureTemplatePool.Projection.RIGID));
        // When I add the real Ninjago City buildings that will use the Jigsaw system for randomization, bases will go here, but for now it's just Jay's empty building
        bootstapContext.register(BUILDINGS, new StructureTemplatePool(empty, ImmutableList.of(
                Pair.of(StructurePoolElement.single("minejago:ninjago_city/buildings/jays_building"), 1)), StructureTemplatePool.Projection.RIGID));
    }
}
