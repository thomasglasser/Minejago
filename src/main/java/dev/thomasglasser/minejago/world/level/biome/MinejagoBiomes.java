package dev.thomasglasser.minejago.world.level.biome;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MinejagoBiomes
{
    public static final ResourceKey<Biome> HIGH_MOUNTAINS = registerKey("high_mountains");

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(Registry.BIOME_REGISTRY, Minejago.MODID);

    public static void registerBiomes(IEventBus bus)
    {
        BIOMES.register(bus);
        register(HIGH_MOUNTAINS, MinejagoOverworldBiomes::highMountains);
    }

    public static RegistryObject<Biome> register(ResourceKey<Biome> key, Supplier<Biome> biomeSupplier)
    {
        return BIOMES.register(key.location().getPath(), biomeSupplier);
    }

    private static ResourceKey<Biome> registerKey(String name)
    {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Minejago.MODID, name));
    }
}
