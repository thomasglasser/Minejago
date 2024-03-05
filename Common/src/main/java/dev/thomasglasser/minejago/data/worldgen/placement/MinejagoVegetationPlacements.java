package dev.thomasglasser.minejago.data.worldgen.placement;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoVegetationFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import static net.minecraft.data.worldgen.placement.VegetationPlacements.treePlacement;

public class MinejagoVegetationPlacements
{	public static final ResourceKey<PlacedFeature> MEADOW_FOCUS_TREES = create("meadow_focus_trees");

	public static void bootstrap(BootstapContext<PlacedFeature> context)
	{
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
		Holder<ConfiguredFeature<?, ?>> focusTrees = configuredFeatures.getOrThrow(MinejagoVegetationFeatures.MEADOW_FOCUS_TREES);
		PlacementUtils.register(context, MEADOW_FOCUS_TREES, focusTrees, treePlacement(RarityFilter.onAverageOnceEvery(100)));
	}

	public static ResourceKey<PlacedFeature> create(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Minejago.modLoc(name));
	}
}
