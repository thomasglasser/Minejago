package dev.thomasglasser.minejago.world.level.levelgen;

import dev.thomasglasser.minejago.Minejago;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class MinejagoNoiseGeneratorSettings {
    public static final ResourceKey<NoiseGeneratorSettings> UNDERWORLD = create("underworld");

    private static ResourceKey<NoiseGeneratorSettings> create(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        context.register(UNDERWORLD, new NoiseGeneratorSettings(
                NoiseSettings.END_NOISE_SETTINGS,
                Blocks.DEEPSLATE.defaultBlockState(),
                Blocks.LAVA.defaultBlockState(),
                NoiseRouterData.end(densityFunctions),
                SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState()),
                List.of(),
                32,
                false,
                false,
                false,
                true));
    }
}
