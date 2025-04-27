package dev.thomasglasser.minejago.core.registries;

import com.mojang.serialization.MapCodec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.tornadoofcreation.TornadoOfCreationConfig;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidType;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class MinejagoRegistries {
    public static final ResourceKey<Registry<Element>> ELEMENT = createRegistryKey("element");
    public static final ResourceKey<Registry<MapCodec<? extends FocusModifier>>> FOCUS_MODIFIER_SERIALIZER = createRegistryKey("focus_modifier_serializer");
    public static final ResourceKey<Registry<FocusModifier>> FOCUS_MODIFIER = createRegistryKey("focus_modifier");
    public static final ResourceKey<Registry<SkulkinRaidType>> SKULKIN_RAID_TYPES = createRegistryKey("skulkin_raid_types");
    public static final ResourceKey<Registry<TornadoOfCreationConfig>> TORNADO_OF_CREATION_CONFIG = createRegistryKey("tornado_of_creation");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String pName) {
        return ResourceKey.createRegistryKey(Minejago.modLoc(pName));
    }

    public static void init() {}
}
