package dev.thomasglasser.minejago.data.worldgen.features;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

public class MinejagoTreeFeatures
{
	public static final ResourceKey<ConfiguredFeature<?, ?>> FOCUS = create("focus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_FOCUS = create("fancy_focus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FOCUS_BEES_0002 = create("focus_bees_0002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FOCUS_BEES_002 = create("focus_bees_002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FOCUS_BEES_005 = create("focus_bees_005");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_FOCUS_BEES_0002 = create("fancy_focus_bees_0002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_FOCUS_BEES_002 = create("fancy_focus_bees_002");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_FOCUS_BEES_005 = create("fancy_focus_bees_005");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_FOCUS_BEES = create("fancy_focus_bees");

	public static TreeConfiguration.TreeConfigurationBuilder createNormal(Supplier<Block> log, Supplier<Block> leaves) {
		return TreeFeatures.createStraightBlobTree(log.get(), leaves.get(), 4, 2, 0, 2).ignoreVines();
	}

	public static TreeConfiguration.TreeConfigurationBuilder createFancy(Supplier<Block> log, Supplier<Block> leaves) {
		return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log.get()), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(leaves.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, Minejago.modLoc(name));
	}

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> pContext) {
		registerTreeSet(pContext, MinejagoBlocks.ENCHANTED_WOOD_SET.get().log(), MinejagoBlocks.FOCUS_LEAVES_SET.get().leaves(), FOCUS, FANCY_FOCUS, FOCUS_BEES_0002, FOCUS_BEES_002, FOCUS_BEES_005, FANCY_FOCUS_BEES_0002, FANCY_FOCUS_BEES_002, FANCY_FOCUS_BEES_005, FANCY_FOCUS_BEES);
	}

	public static void registerTreeSet(BootstrapContext<ConfiguredFeature<?, ?>> pContext, Supplier<Block> log, Supplier<Block> leaves, ResourceKey<ConfiguredFeature<?, ?>> normal, ResourceKey<ConfiguredFeature<?, ?>> fancy, ResourceKey<ConfiguredFeature<?, ?>> normalBees0002, ResourceKey<ConfiguredFeature<?, ?>> normalBees002, ResourceKey<ConfiguredFeature<?, ?>> normalBees005, ResourceKey<ConfiguredFeature<?, ?>> fancyBees0002, ResourceKey<ConfiguredFeature<?, ?>> fancyBees002, ResourceKey<ConfiguredFeature<?, ?>> fancyBees005, ResourceKey<ConfiguredFeature<?, ?>> fancyBees)
	{
		BeehiveDecorator beehivedecorator = new BeehiveDecorator(0.002F);
		BeehiveDecorator beehivedecorator2 = new BeehiveDecorator(0.02F);
		BeehiveDecorator beehivedecorator3 = new BeehiveDecorator(0.05F);
		BeehiveDecorator beehivedecorator4 = new BeehiveDecorator(1.0F);
		FeatureUtils.register(pContext, normal, Feature.TREE, createNormal(log, leaves).build());
		FeatureUtils.register(pContext, fancy, Feature.TREE, createFancy(log, leaves).build());
		FeatureUtils.register(pContext, normalBees0002, Feature.TREE, createNormal(log, leaves).decorators(List.of(beehivedecorator)).build());
		FeatureUtils.register(pContext, normalBees002, Feature.TREE, createNormal(log, leaves).decorators(List.of(beehivedecorator2)).build());
		FeatureUtils.register(pContext, normalBees005, Feature.TREE, createNormal(log, leaves).decorators(List.of(beehivedecorator3)).build());
		FeatureUtils.register(pContext, fancyBees0002, Feature.TREE, createFancy(log, leaves).decorators(List.of(beehivedecorator)).build());
		FeatureUtils.register(pContext, fancyBees002, Feature.TREE, createFancy(log, leaves).decorators(List.of(beehivedecorator2)).build());
		FeatureUtils.register(pContext, fancyBees005, Feature.TREE, createFancy(log, leaves).decorators(List.of(beehivedecorator3)).build());
		FeatureUtils.register(pContext, fancyBees, Feature.TREE, createFancy(log, leaves).decorators(List.of(beehivedecorator4)).build());
	}
}
