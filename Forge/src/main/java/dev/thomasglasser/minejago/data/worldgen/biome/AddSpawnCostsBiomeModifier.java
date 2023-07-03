package dev.thomasglasser.minejago.data.worldgen.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public record AddSpawnCostsBiomeModifier(HolderSet<Biome> biomes, EntityType<?> entityType, MobSpawnSettings.MobSpawnCost cost) implements BiomeModifier
{
    /**
     * Convenience method for using a single spawn data.
     * @param biomes Biomes to add mob spawns to.
     * @param cost Cost
     * @return AddSpawnCostsBiomeModifier that adds a single spawn cost
     */
    public static AddSpawnCostsBiomeModifier addCost(HolderSet<Biome> biomes, EntityType<?> entityType, MobSpawnSettings.MobSpawnCost cost)
    {
        return new AddSpawnCostsBiomeModifier(biomes, entityType, cost);
    }

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder)
    {
        if (phase == Phase.ADD && this.biomes.contains(biome))
        {
            builder.getMobSpawnSettings().addMobCharge(entityType, cost.charge(), cost.energyBudget());
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec()
    {
        return MinejagoBiomeModifierSerializers.ADD_SPAWN_COSTS.get();
    }
}