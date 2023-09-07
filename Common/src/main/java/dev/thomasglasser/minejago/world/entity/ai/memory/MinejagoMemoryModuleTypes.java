package dev.thomasglasser.minejago.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;

public class MinejagoMemoryModuleTypes
{
    public static final RegistrationProvider<MemoryModuleType<?>> MEMORY_MODULE_TYPES = RegistrationProvider.get(Registries.MEMORY_MODULE_TYPE, Minejago.MOD_ID);

    public static final RegistryObject<MemoryModuleType<ResourceKey<Power>>> SELECTED_POWER = register("selected_power", ResourceKey.codec(MinejagoRegistries.POWER));

    public static <T> RegistryObject<MemoryModuleType<T>> register(String id, Codec<T> codec)
    {
        return MEMORY_MODULE_TYPES.register(id, () -> new MemoryModuleType<T>(Optional.of(codec)));
    }

    public static void init() {}
}
