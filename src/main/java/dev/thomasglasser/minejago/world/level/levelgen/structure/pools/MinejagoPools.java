package dev.thomasglasser.minejago.world.level.levelgen.structure.pools;

import static net.minecraft.data.worldgen.Pools.EMPTY;

import com.google.common.collect.ImmutableList;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class MinejagoPools {
    public static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> holderGetter = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> holder = holderGetter.getOrThrow(EMPTY);
        context.register(EMPTY, new StructureTemplatePool(holder, ImmutableList.of(), StructureTemplatePool.Projection.RIGID));

        FourWeaponsPools.bootstrap(context);
        CaveOfDespairPools.bootstrap(context);
        NinjagoCityPools.bootstrap(context);
        MonasteryOfSpinjitzuPools.bootstrap(context);
    }
}
