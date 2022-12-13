package dev.thomasglasser.minejago.world.level.biome;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MinejagoBiomes
{
    public static final ResourceKey<Biome> HIGH_MOUNTAINS = modLoc("high_mountains");

    private static ResourceKey<Biome> modLoc(String name)
    {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Minejago.MOD_ID, name));
    }
}
