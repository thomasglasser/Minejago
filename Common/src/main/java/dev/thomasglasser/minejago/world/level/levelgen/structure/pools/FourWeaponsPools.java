package dev.thomasglasser.minejago.world.level.levelgen.structure.pools;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import static net.minecraft.data.worldgen.Pools.EMPTY;

public class FourWeaponsPools
{
    public static final ResourceKey<StructureTemplatePool> START = MinejagoPools.createKey("four_weapons/base");

    public static void bootstrap(BootstapContext<StructureTemplatePool> bootstapContext) {
        HolderGetter<StructureTemplatePool> holderGetter = bootstapContext.lookup(Registries.TEMPLATE_POOL);

        Holder<StructureTemplatePool> holder = holderGetter.getOrThrow(EMPTY);

        bootstapContext.register(
                START,
                new StructureTemplatePool(
                        holder, ImmutableList.of(Pair.of(MinejagoPools.legacyElement(Minejago.modLoc("four_weapons/base")), 1)), StructureTemplatePool.Projection.RIGID
                )
        );
    }
}
