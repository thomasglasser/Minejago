package dev.thomasglasser.minejago.world.level.levelgen.structure.pools;

import static net.minecraft.data.worldgen.Pools.EMPTY;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class FourWeaponsPools {
    public static final ResourceKey<StructureTemplatePool> START = MinejagoPools.createKey("four_weapons/base");

    public static void bootstrap(BootstrapContext<StructureTemplatePool> bootstrapContext) {
        HolderGetter<StructureTemplatePool> holderGetter = bootstrapContext.lookup(Registries.TEMPLATE_POOL);

        Holder<StructureTemplatePool> holder = holderGetter.getOrThrow(EMPTY);

        bootstrapContext.register(
                START,
                new StructureTemplatePool(
                        holder, ImmutableList.of(Pair.of(MinejagoPools.legacyElement(Minejago.modLoc("four_weapons/base")), 1)), StructureTemplatePool.Projection.RIGID));
    }
}
