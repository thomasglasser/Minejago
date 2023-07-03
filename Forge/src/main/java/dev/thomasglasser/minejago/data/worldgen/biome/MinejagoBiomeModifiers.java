package dev.thomasglasser.minejago.data.worldgen.biome;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class MinejagoBiomeModifiers
{
    private static ResourceKey<BiomeModifier> register(String name)
    {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context)
    {
        // Add spawns
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderSet<Biome> mountains = biomes.get(BiomeTags.IS_MOUNTAIN).orElseThrow();
        context.register(register("add_cole"), addCharactersToBiomes(mountains, MinejagoEntityTypes.COLE.get()));
        Holder.Reference<Biome> frozen_lakes = biomes.get(Biomes.FROZEN_RIVER).orElseThrow();
        context.register(register("add_zane"), addCharactersToBiomes(HolderSet.direct(frozen_lakes), MinejagoEntityTypes.ZANE.get()));

        // Add charges
        context.register(register("charge_cole"), addCharacterCharge(mountains, MinejagoEntityTypes.COLE.get()));
        context.register(register("charge_zane"), new AddSpawnCostsBiomeModifier(HolderSet.direct(frozen_lakes), MinejagoEntityTypes.ZANE.get(), new MobSpawnSettings.MobSpawnCost(0.15, 0.7)));
    }

    @SafeVarargs
    private static ForgeBiomeModifiers.AddSpawnsBiomeModifier addCharactersToBiomes(HolderSet<Biome> biomes, EntityType<? extends Entity>... entities)
    {
        List<MobSpawnSettings.SpawnerData> data = new ArrayList<>();

        for (EntityType<?> entity : entities)
        {
            data.add(new MobSpawnSettings.SpawnerData(
                    entity,
                    1,
                    1,
                    1
            ));
        }

        return new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes,
                data
        );
    }

    private static AddSpawnCostsBiomeModifier addCharacterCharge(HolderSet<Biome> biomes, EntityType<? extends Entity> entity)
    {
        return new AddSpawnCostsBiomeModifier(
                biomes,
                entity,
                new MobSpawnSettings.MobSpawnCost(0.3, 1.0)
        );
    }
}
