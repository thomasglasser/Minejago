package dev.thomasglasser.minejago.world.level.levelgen.structure.placement;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class MinejagoStructureSets
{
    public static final ResourceKey<StructureSet> FOUR_WEAPONS = register("four_weapons");

    public static void bootstrap(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> holderGetter = context.lookup(Registries.STRUCTURE);

        context.register(
                FOUR_WEAPONS,
                new StructureSet(holderGetter.getOrThrow(MinejagoStructures.FOUR_WEAPONS), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14357613))
        );
    }

    private static ResourceKey<StructureSet> register(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, Minejago.modLoc(name));
    }
}
