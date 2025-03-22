package dev.thomasglasser.minejago.world.level.biome;

import com.mojang.serialization.MapCodec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;

public class MinejagoBiomeSources {
    private static final DeferredRegister<MapCodec<? extends BiomeSource>> BIOME_SOURCES = DeferredRegister.create(Registries.BIOME_SOURCE, Minejago.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BiomeSource>, MapCodec<UnderworldBiomeSource>> UNDERWORLD = register("underworld", () -> UnderworldBiomeSource.CODEC);

    private static <T extends BiomeSource> DeferredHolder<MapCodec<? extends BiomeSource>, MapCodec<T>> register(String name, Supplier<MapCodec<T>> codec) {
        return BIOME_SOURCES.register(name, codec);
    }

    public static void init() {}
}
