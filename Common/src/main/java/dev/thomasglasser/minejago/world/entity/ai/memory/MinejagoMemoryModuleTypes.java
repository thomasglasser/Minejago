package dev.thomasglasser.minejago.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;
import java.util.function.Supplier;

public class MinejagoMemoryModuleTypes
{
    public static final Supplier<MemoryModuleType<ResourceKey<Power>>> SELECTED_POWER = register("selected_power", ResourceKey.codec(MinejagoRegistries.POWER));

    public static <T> Supplier<MemoryModuleType<T>> register(String id, Codec<T> codec)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.MEMORY_MODULE_TYPE, id, () -> new MemoryModuleType<T>(Optional.of(codec)));
    }

    public static void init() {}
}
