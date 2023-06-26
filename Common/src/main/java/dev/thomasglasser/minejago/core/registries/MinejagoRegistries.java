package dev.thomasglasser.minejago.core.registries;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class MinejagoRegistries
{
    public static final ResourceKey<Registry<Power>> POWER = createRegistryKey("power");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String pName) {
        return ResourceKey.createRegistryKey(Minejago.modLoc(pName));
    }

    public static void init() {}
}
