package dev.thomasglasser.minejago.data.worldgen.biome;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.tags.MinejagoBiomeTags;
import dev.thomasglasser.minejago.data.worldgen.placement.MinejagoVegetationPlacements;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class MinejagoBiomeModifiers
{
    private static ResourceKey<BiomeModifier> register(String name)
    {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context)
    {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        // Add spawns
        HolderSet<Biome> mountains = biomes.get(BiomeTags.IS_MOUNTAIN).orElseThrow();
        context.register(register("add_cole"), addCharactersToBiomes(mountains, MinejagoEntityTypes.COLE.get()));
        Holder.Reference<Biome> frozen_lakes = biomes.get(Biomes.FROZEN_RIVER).orElseThrow();
        context.register(register("add_zane"), addCharactersToBiomes(HolderSet.direct(frozen_lakes), 100, MinejagoEntityTypes.ZANE.get()));

        // Add charges
        context.register(register("charge_cole"), addCharge(mountains, MinejagoEntityTypes.COLE.get(), 0.15, 0.7));
//        context.register(register("charge_zane"), addCharge(HolderSet.direct(frozen_lakes), MinejagoEntityTypes.ZANE.get(), 0.3, 1.0));

        // Add features
        Holder.Reference<PlacedFeature> focusTrees = placedFeatures.getOrThrow(MinejagoVegetationPlacements.MEADOW_FOCUS_TREES);
        context.register(register("add_focus_trees"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(MinejagoBiomeTags.HAS_FOCUS_TREES), HolderSet.direct(focusTrees), GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    @SafeVarargs
    private static ForgeBiomeModifiers.AddSpawnsBiomeModifier addCharactersToBiomes(HolderSet<Biome> biomes, EntityType<? extends Entity>... entities)
    {
        return addCharactersToBiomes(biomes, 1, entities);
    }

    @SafeVarargs
    private static ForgeBiomeModifiers.AddSpawnsBiomeModifier addCharactersToBiomes(HolderSet<Biome> biomes, int weight, EntityType<? extends Entity>... entities)
    {
        List<MobSpawnSettings.SpawnerData> data = new ArrayList<>();

        for (EntityType<?> entity : entities)
        {
            data.add(new MobSpawnSettings.SpawnerData(
                    entity,
                    weight,
                    1,
                    1
            ));
        }

        return new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes,
                data
        );
    }

    private static AddSpawnCostsBiomeModifier addCharge(HolderSet<Biome> biomes, EntityType<? extends Entity> entity, double budget, double charge)
    {
        return new AddSpawnCostsBiomeModifier(
                biomes,
                entity,
                new MobSpawnSettings.MobSpawnCost(budget, charge)
        );
    }
}
