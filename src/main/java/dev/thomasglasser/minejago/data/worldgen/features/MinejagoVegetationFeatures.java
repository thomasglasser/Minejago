package dev.thomasglasser.minejago.data.worldgen.features;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.worldgen.placement.MinejagoTreePlacements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class MinejagoVegetationFeatures
{
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEADOW_FOCUS_TREES = create("meadow_focus_trees");

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context)
	{
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		Holder.Reference<PlacedFeature> focusFancyBees0002 = placedFeatures.getOrThrow(MinejagoTreePlacements.FANCY_FOCUS_BEES_0002);
		FeatureUtils.register(context, MEADOW_FOCUS_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(focusFancyBees0002, 0.1f)), focusFancyBees0002));
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Minejago.modLoc(name));
	}
}
