package dev.thomasglasser.minejago.world.level.biome;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class MinejagoBiomes {
    public static final ResourceKey<Biome> UNDERWORLD_CENTER = create("underworld_center");
    public static final ResourceKey<Biome> UNDERWORLD_ISLANDS = create("underworld_islands");

    private static ResourceKey<Biome> create(String name) {
        return ResourceKey.create(Registries.BIOME, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(UNDERWORLD_CENTER, underworldCenter(placedFeatures, worldCarvers));
        context.register(UNDERWORLD_ISLANDS, underworldIslands(placedFeatures, worldCarvers));
    }

    private static void underworldSpawns(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(MinejagoEntityTypes.SPYKOR.get(), 10, 1, 6));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(MinejagoEntityTypes.SKULKIN.get(), 8, 1, 4));
        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(MinejagoEntityTypes.SKULKIN_HORSE.get(), 8, 1, 2));
    }

    private static Biome baseUnderworldBiome(BiomeGenerationSettings.Builder generationSettings) {
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        underworldSpawns(mobspawnsettings$builder);
        return (new Biome.BiomeBuilder()).hasPrecipitation(false).temperature(0.5F).downfall(0.5F).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(0x673fe4).waterFogColor(0x1c0533).fogColor(0x180829).skyColor(0).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawnsettings$builder.build()).generationSettings(generationSettings.build()).build();
    }

    public static Biome underworldCenter(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers));
        return baseUnderworldBiome(biomegenerationsettings$builder);
    }

    public static Biome underworldIslands(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers));
        return baseUnderworldBiome(biomegenerationsettings$builder);
    }
}
