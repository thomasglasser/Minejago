package dev.thomasglasser.minejago.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;

public class MinejagoMemoryModuleTypes
{
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, Minejago.MOD_ID);

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<ResourceKey<Power>>> SELECTED_POWER = register("selected_power", ResourceKey.codec(MinejagoRegistries.POWER));

    public static <T> DeferredHolder<MemoryModuleType<?>, MemoryModuleType<T>> register(String id, Codec<T> codec)
    {
        return MEMORY_MODULE_TYPES.register(id, () -> new MemoryModuleType<T>(Optional.of(codec)));
    }

    public static void init() {}
}
