package dev.thomasglasser.minejago.core.registries;

import com.mojang.serialization.MapCodec;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class MinejagoBuiltInRegistries {
    public static final Registry<MapCodec<? extends FocusModifier>> FOCUS_MODIFIER_SERIALIZER = new RegistryBuilder<>(MinejagoRegistries.FOCUS_MODIFIER_SERIALIZER).create();

    public static void init() {}
}
