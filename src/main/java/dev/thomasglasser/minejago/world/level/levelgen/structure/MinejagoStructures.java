package dev.thomasglasser.minejago.world.level.levelgen.structure;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBiomeTags;
import dev.thomasglasser.minejago.world.level.levelgen.structure.pools.CaveOfDespairPools;
import dev.thomasglasser.minejago.world.level.levelgen.structure.pools.FourWeaponsPools;
import dev.thomasglasser.minejago.world.level.levelgen.structure.pools.NinjagoCityPools;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Map;

public class MinejagoStructures
{
    public static final ResourceKey<Structure> FOUR_WEAPONS = createKey("four_weapons");
    public static final ResourceKey<Structure> CAVE_OF_DESPAIR = createKey("cave_of_despair");
    public static final ResourceKey<Structure> NINJAGO_CITY = createKey("ninjago_city");

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<Structure> context)
    {
        HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> holderGetter2 = context.lookup(Registries.TEMPLATE_POOL);

        context.register(
                FOUR_WEAPONS,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(holderGetter.getOrThrow(MinejagoBiomeTags.HAS_FOUR_WEAPONS))
                                .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                                .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                                .build(),
                        holderGetter2.getOrThrow(FourWeaponsPools.START),
                        7,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );

        context.register(
                CAVE_OF_DESPAIR,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(holderGetter.getOrThrow(MinejagoBiomeTags.HAS_CAVE_OF_DESPAIR))
                                .generationStep(GenerationStep.Decoration.UNDERGROUND_STRUCTURES)
                                .terrainAdapation(TerrainAdjustment.BURY)
                                .spawnOverrides(
                                        Map.of(
                                                MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create()),
                                                MobCategory.AMBIENT, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create())
                                        )
                                )
                                .build(),
                        holderGetter2.getOrThrow(CaveOfDespairPools.START),
                        7,
                        ConstantHeight.of(VerticalAnchor.absolute(40)),
                        false
                )
        );

        context.register(NINJAGO_CITY,
                new JigsawStructure(
                        new Structure.StructureSettings.Builder(holderGetter.getOrThrow(MinejagoBiomeTags.HAS_NINJAGO_CITY))
                                .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                                .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                                .build(),
                        holderGetter2.getOrThrow(NinjagoCityPools.BUILDINGS),
                        7,
                        ConstantHeight.of(VerticalAnchor.absolute(0)),
                        false,
                        Heightmap.Types.WORLD_SURFACE_WG
                )
        );
    }
}
