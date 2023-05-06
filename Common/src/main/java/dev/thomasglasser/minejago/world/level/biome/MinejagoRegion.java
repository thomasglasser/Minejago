package dev.thomasglasser.minejago.world.level.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.worldgen.RegionUtils;

import java.util.function.Consumer;

public class MinejagoRegion extends Region
{
    public MinejagoRegion(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        RegionUtils.getVanillaParameterPoints(Biomes.SNOWY_SLOPES).forEach(point ->
        {
            this.addBiome(mapper, new Climate.ParameterPoint(point.temperature(), point.humidity(), point.continentalness(), Climate.Parameter.point(0.01f), point.depth(), point.weirdness(), point.offset()), MinejagoBiomes.HIGH_MOUNTAINS);
        });
    }
}