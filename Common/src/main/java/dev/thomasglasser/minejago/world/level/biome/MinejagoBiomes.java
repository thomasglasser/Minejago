package dev.thomasglasser.minejago.world.level.biome;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class MinejagoBiomes
{
    public static final ResourceKey<Biome> HIGH_MOUNTAINS = modLoc("high_mountains");

    private static ResourceKey<Biome> modLoc(String name)
    {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Minejago.MOD_ID, name));
    }
}
