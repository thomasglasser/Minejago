package dev.thomasglasser.minejago.world.level.levelgen.structure.placement;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class MinejagoStructureSets {
    public static final ResourceKey<StructureSet> FOUR_WEAPONS = register("four_weapons");
    public static final ResourceKey<StructureSet> CAVE_OF_DESPAIR = register("cave_of_despair");
    public static final ResourceKey<StructureSet> NINJAGO_CITY = register("ninjago_city");
    public static final ResourceKey<StructureSet> MONASTERY_OF_SPINJITZU = register("monastery_of_spinjitzu");

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> holderGetter = context.lookup(Registries.STRUCTURE);

        // Fun Easter egg: the number used for four weapons and the starting number is the word "ninja" numerically
        context.register(
                FOUR_WEAPONS,
                new StructureSet(holderGetter.getOrThrow(MinejagoStructures.FOUR_WEAPONS), new RandomSpreadStructurePlacement(64, 16, RandomSpreadType.LINEAR, 14914101)));

        context.register(
                CAVE_OF_DESPAIR,
                new StructureSet(holderGetter.getOrThrow(MinejagoStructures.CAVE_OF_DESPAIR), new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14914102)));

        context.register(
                NINJAGO_CITY,
                new StructureSet(holderGetter.getOrThrow(MinejagoStructures.NINJAGO_CITY), new RandomSpreadStructurePlacement(128, 32, RandomSpreadType.LINEAR, 14914103)));

        context.register(
                MONASTERY_OF_SPINJITZU,
                new StructureSet(holderGetter.getOrThrow(MinejagoStructures.MONASTERY_OF_SPINJITZU), new RandomSpreadStructurePlacement(128, 32, RandomSpreadType.LINEAR, 14914104)));
    }

    private static ResourceKey<StructureSet> register(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, Minejago.modLoc(name));
    }
}
