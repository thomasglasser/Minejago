package dev.thomasglasser.minejago.core.registries;

import com.mojang.serialization.MapCodec;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidType;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class MinejagoBuiltInRegistries {
    public static final Registry<MapCodec<? extends FocusModifier>> FOCUS_MODIFIER_SERIALIZER = new RegistryBuilder<>(MinejagoRegistries.FOCUS_MODIFIER_SERIALIZER).create();
    public static final Registry<SkulkinRaidType> SKULKIN_RAID_TYPES = new RegistryBuilder<>(MinejagoRegistries.SKULKIN_RAID_TYPES).create();

    public static void init() {}
}
