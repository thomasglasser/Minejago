package dev.thomasglasser.minejago.core.registries;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

public class MinejagoRegistries
{
    public static final Supplier<Registry<Power>> POWER = Services.REGISTRY.createRegistry("power", MinejagoRegistryKeys.POWER, Power.class, MinejagoPowers.NONE);

    public static void init() {}
}
