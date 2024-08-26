package dev.thomasglasser.minejago.core.component;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.Nullable;

public class MinejagoDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Minejago.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceKey<Power>>> POWER = register("power", ResourceKey.codec(MinejagoRegistries.POWER), ResourceKey.streamCodec(MinejagoRegistries.POWER), false);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> GOLDEN_WEAPONS_MAP = register("golden_weapons_map_data", Codec.unit(Unit.INSTANCE), StreamCodec.unit(Unit.INSTANCE), false);

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, @Nullable Codec<T> diskCodec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> networkCodec, boolean cache) {
        Supplier<DataComponentType<T>> component = () -> {
            DataComponentType.Builder<T> builder = DataComponentType.builder();
            if (diskCodec != null) {
                builder.persistent(diskCodec);
            }
            if (networkCodec != null) {
                builder.networkSynchronized(networkCodec);
            }
            if (cache) {
                builder.cacheEncoding();
            }
            return builder.build();
        };
        return DATA_COMPONENTS.register(name, component);
    }

    public static void init() {}
}
