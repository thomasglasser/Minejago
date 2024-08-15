package dev.thomasglasser.minejago.data.worldgen.biome;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.worldgen.placement.MinejagoVegetationPlacements;
import dev.thomasglasser.minejago.tags.MinejagoBiomeTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MinejagoBiomeModifiers {
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        // Add spawns
        HolderSet<Biome> mountains = biomes.get(BiomeTags.IS_MOUNTAIN).orElseThrow();
        context.register(register("add_cole"), addCharactersToBiomes(mountains, 100, MinejagoEntityTypes.COLE.get()));
        Holder.Reference<Biome> frozen_river = biomes.get(Biomes.FROZEN_RIVER).orElseThrow();
        context.register(register("add_zane"), addCharactersToBiomes(HolderSet.direct(frozen_river), 100, MinejagoEntityTypes.ZANE.get()));

        // Add features
        Holder.Reference<PlacedFeature> focusTrees = placedFeatures.getOrThrow(MinejagoVegetationPlacements.MEADOW_FOCUS_TREES);
        context.register(register("add_focus_trees"), new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(MinejagoBiomeTags.HAS_FOCUS_TREES), HolderSet.direct(focusTrees), GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> register(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Minejago.modLoc(name));
    }

    @SafeVarargs
    private static BiomeModifiers.AddSpawnsBiomeModifier addCharactersToBiomes(HolderSet<Biome> biomes, EntityType<? extends Entity>... entities) {
        return addCharactersToBiomes(biomes, 1, entities);
    }

    @SafeVarargs
    private static BiomeModifiers.AddSpawnsBiomeModifier addCharactersToBiomes(HolderSet<Biome> biomes, int weight, EntityType<? extends Entity>... entities) {
        List<MobSpawnSettings.SpawnerData> data = new ArrayList<>();

        for (EntityType<?> entity : entities) {
            data.add(new MobSpawnSettings.SpawnerData(
                    entity,
                    weight,
                    1,
                    1));
        }

        return new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes,
                data);
    }
}
