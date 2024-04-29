package dev.thomasglasser.minejago.data.worldgen.placement;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoTreeFeatures;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public class MinejagoTreePlacements
{
	public static final ResourceKey<PlacedFeature> FOCUS_CHECKED = create("focus_checked");
	public static final ResourceKey<PlacedFeature> FANCY_FOCUS_CHECKED = create("fancy_focus_checked");
	public static final ResourceKey<PlacedFeature> FOCUS_BEES_0002 = create("focus_bees_0002");
	public static final ResourceKey<PlacedFeature> FOCUS_BEES_002 = create("focus_bees_002");
	public static final ResourceKey<PlacedFeature> FANCY_FOCUS_BEES_0002 = create("fancy_focus_bees_0002");
	public static final ResourceKey<PlacedFeature> FANCY_FOCUS_BEES_002 = create("fancy_focus_bees_002");
	public static final ResourceKey<PlacedFeature> FANCY_FOCUS_BEES = create("fancy_focus_bees");

	public static void bootstrap(BootstrapContext<PlacedFeature> pContext) {
		HolderGetter<ConfiguredFeature<?, ?>> features = pContext.lookup(Registries.CONFIGURED_FEATURE);
		registerTreeSet(pContext, features, MinejagoBlocks.FOCUS_LEAVES_SET.sapling(),
				MinejagoTreeFeatures.FOCUS, MinejagoTreeFeatures.FANCY_FOCUS, MinejagoTreeFeatures.FOCUS_BEES_0002, MinejagoTreeFeatures.FOCUS_BEES_002, MinejagoTreeFeatures.FANCY_FOCUS_BEES_0002, MinejagoTreeFeatures.FANCY_FOCUS_BEES_002, MinejagoTreeFeatures.FANCY_FOCUS_BEES,
				FOCUS_CHECKED, FANCY_FOCUS_CHECKED, FOCUS_BEES_0002, FOCUS_BEES_002, FANCY_FOCUS_BEES_0002, FANCY_FOCUS_BEES_002, FANCY_FOCUS_BEES);
	}

	public static void registerTreeSet(BootstrapContext<PlacedFeature> pContext, HolderGetter<ConfiguredFeature<?, ?>> features, Supplier<Block> sapling, ResourceKey<ConfiguredFeature<?, ?>> normalConfigured, ResourceKey<ConfiguredFeature<?, ?>> fancyConfigured, ResourceKey<ConfiguredFeature<?, ?>> normalBees0002Configured, ResourceKey<ConfiguredFeature<?, ?>> normalBees002Configured, ResourceKey<ConfiguredFeature<?, ?>> fancyBees0002Configured, ResourceKey<ConfiguredFeature<?, ?>> fancyBees002Configured, ResourceKey<ConfiguredFeature<?, ?>> fancyBeesConfigured, ResourceKey<PlacedFeature> normal, ResourceKey<PlacedFeature> fancy, ResourceKey<PlacedFeature> normalBees0002, ResourceKey<PlacedFeature> normalBees002, ResourceKey<PlacedFeature> fancyBees0002, ResourceKey<PlacedFeature> fancyBees002, ResourceKey<PlacedFeature> fancyBees)
	{
		Holder<ConfiguredFeature<?, ?>> holder2 = features.getOrThrow(normalConfigured);
		Holder<ConfiguredFeature<?, ?>> holder11 = features.getOrThrow(fancyConfigured);
		Holder<ConfiguredFeature<?, ?>> holder19 = features.getOrThrow(normalBees0002Configured);
		Holder<ConfiguredFeature<?, ?>> holder20 = features.getOrThrow(normalBees002Configured);
		Holder<ConfiguredFeature<?, ?>> holder23 = features.getOrThrow(fancyBees0002Configured);
		Holder<ConfiguredFeature<?, ?>> holder24 = features.getOrThrow(fancyBees002Configured);
		Holder<ConfiguredFeature<?, ?>> holder25 = features.getOrThrow(fancyBeesConfigured);
		PlacementUtils.register(pContext, normal, holder2, PlacementUtils.filteredByBlockSurvival(sapling.get()));
		PlacementUtils.register(pContext, fancy, holder11, PlacementUtils.filteredByBlockSurvival(sapling.get()));
		PlacementUtils.register(pContext, normalBees0002, holder19, PlacementUtils.filteredByBlockSurvival(sapling.get()));
		PlacementUtils.register(pContext, normalBees002, holder20, PlacementUtils.filteredByBlockSurvival(sapling.get()));
		PlacementUtils.register(pContext, fancyBees0002, holder23, PlacementUtils.filteredByBlockSurvival(sapling.get()));
		PlacementUtils.register(pContext, fancyBees002, holder24, PlacementUtils.filteredByBlockSurvival(sapling.get()));
		PlacementUtils.register(pContext, fancyBees, holder25, PlacementUtils.filteredByBlockSurvival(sapling.get()));
	}

	private static ResourceKey<PlacedFeature> create(String pKey) {
		return ResourceKey.create(Registries.PLACED_FEATURE, Minejago.modLoc(pKey));
	}
}
