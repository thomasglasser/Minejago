package dev.thomasglasser.minejago.core.registries;

import com.mojang.serialization.MapCodec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class MinejagoRegistries {
    public static final ResourceKey<Registry<Power>> POWER = createRegistryKey("power");
    public static final ResourceKey<Registry<MapCodec<? extends FocusModifier>>> FOCUS_MODIFIER_SERIALIZER = createRegistryKey("focus_modifier_serializer");
    public static final ResourceKey<Registry<FocusModifier>> FOCUS_MODIFIER = createRegistryKey("focus_modifier");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String pName) {
        return ResourceKey.createRegistryKey(Minejago.modLoc(pName));
    }

    public static void init() {}
}
