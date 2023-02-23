package dev.thomasglasser.minejago.world.entity.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.registration.registries.DatapackRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;

public class MinejagoPowers {

    public static final Function<RegistryAccess, Registry<Power>> POWERS = DatapackRegistryHelper.INSTANCE.createRegistry(MinejagoRegistries.POWER, Power.CODEC, null);

    public static final ResourceKey<Power> NONE = create("none");
    public static final ResourceKey<Power> ICE = create("ice");
    public static final ResourceKey<Power> EARTH = create("earth");
    public static final ResourceKey<Power> FIRE = create("fire");
    public static final ResourceKey<Power> LIGHTNING = create("lightning");

    private static ResourceKey<Power> create(String id)
    {
        return ResourceKey.create(MinejagoRegistries.POWER, Minejago.modLoc(id));
    }

    public static void init() {}
}
