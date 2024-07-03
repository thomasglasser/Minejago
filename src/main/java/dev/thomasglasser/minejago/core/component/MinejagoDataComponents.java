package dev.thomasglasser.minejago.core.component;

import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.Nullable;

public class MinejagoDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Minejago.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> POWER = register("power", ResourceLocation.CODEC, ResourceLocation.STREAM_CODEC, false);
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
            return builder.cacheEncoding().build();
        };
        return DATA_COMPONENTS.register(name, component);
    }

    public static void init() {}
}
