package dev.thomasglasser.minejago.world.level.levelgen.structure.pools;

import com.mojang.datafixers.util.Either;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.function.Function;

public class MinejagoPools
{
    public static ResourceKey<StructureTemplatePool> createKey(String name) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        FourWeaponsPools.bootstrap(context);
    }

    public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> legacyElement(ResourceLocation rl) {
        return projection -> new LegacySinglePoolElement(Either.left(rl),
                Holder.direct(new StructureProcessorList(List.of())), projection);
    }
}
