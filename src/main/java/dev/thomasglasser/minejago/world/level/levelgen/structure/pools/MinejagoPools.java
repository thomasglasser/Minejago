package dev.thomasglasser.minejago.world.level.levelgen.structure.pools;

import static net.minecraft.data.worldgen.Pools.EMPTY;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import dev.thomasglasser.minejago.Minejago;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

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
    }

    public static Function<StructureTemplatePool.Projection, SinglePoolElement> singleElement(ResourceLocation rl) {
        return projection -> new SinglePoolElement(Either.left(rl), Holder.direct(new StructureProcessorList(List.of())), projection, Optional.empty());
    }

    public static Function<StructureTemplatePool.Projection, SinglePoolElement> singleElement(ResourceLocation rl, Holder<StructureProcessorList> structureProcessorList) {
        return projection -> new SinglePoolElement(Either.left(rl),
                structureProcessorList, projection, Optional.empty());
    }

    public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> legacyElement(ResourceLocation rl) {
        return projection -> new LegacySinglePoolElement(Either.left(rl),
                Holder.direct(new StructureProcessorList(List.of())), projection, Optional.empty());
    }
}
