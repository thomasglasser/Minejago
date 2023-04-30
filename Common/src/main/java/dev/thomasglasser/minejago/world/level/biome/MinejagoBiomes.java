package dev.thomasglasser.minejago.world.level.biome;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class MinejagoBiomes
{
    public static final ResourceKey<Biome> HIGH_MOUNTAINS = modLoc("high_mountains");

    private static ResourceKey<Biome> modLoc(String name)
    {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Minejago.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<Biome> context)
    {
        context.register(HIGH_MOUNTAINS, highMountains(context));
    }

    private static Biome highMountains(BootstapContext<Biome> context)
    {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = context.lookup(Registries.CONFIGURED_CARVER);
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        MobSpawnSettings.Builder builder2 = new MobSpawnSettings.Builder();
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_STONY_PEAKS);
        return OverworldBiomes.biome(true, 1.0f, 0.3f, builder2, builder, music);
    }
}
