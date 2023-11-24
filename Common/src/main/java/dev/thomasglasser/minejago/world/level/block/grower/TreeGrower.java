package dev.thomasglasser.minejago.world.level.block.grower;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class TreeGrower extends AbstractTreeGrower
{
	private final ResourceKey<ConfiguredFeature<?, ?>> normal, fancy, bees, fancyBees;
	public TreeGrower(ResourceKey<ConfiguredFeature<?, ?>> normal, ResourceKey<ConfiguredFeature<?, ?>> fancy, @Nullable ResourceKey<ConfiguredFeature<?, ?>> bees, @Nullable ResourceKey<ConfiguredFeature<?, ?>> fancyBees)
	{
		this.normal = normal;
		this.bees = bees;
		this.fancy = fancy;
		this.fancyBees = fancyBees;
	}

	@Nullable
	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers)
	{
		if (random.nextInt(10) == 0) {
			return hasFlowers && fancyBees != null ? fancyBees : fancy;
		} else {
			return hasFlowers && bees != null ? bees : normal;
		}
	}
}
