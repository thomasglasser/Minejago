package dev.thomasglasser.minejago.world.level.dimension;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.biome.UnderworldBiomeSource;
import dev.thomasglasser.minejago.world.level.levelgen.MinejagoNoiseGeneratorSettings;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class MinejagoLevelStems {
    public static final ResourceKey<LevelStem> UNDERWORLD = create("underworld");

    private static ResourceKey<LevelStem> create(String name) {
        return ResourceKey.create(Registries.LEVEL_STEM, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<LevelStem> context) {
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        context.register(UNDERWORLD, new LevelStem(dimensionTypes.getOrThrow(MinejagoDimensionTypes.UNDERWORLD), new NoiseBasedChunkGenerator(
                UnderworldBiomeSource.create(biomes),
                noiseSettings.getOrThrow(MinejagoNoiseGeneratorSettings.UNDERWORLD))));
    }
}
