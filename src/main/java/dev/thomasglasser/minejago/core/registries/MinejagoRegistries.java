package dev.thomasglasser.minejago.core.registries;

import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class MinejagoRegistries {
    public static Supplier<IForgeRegistry<Power>> POWERS = MinejagoPowers.REGISTRY;
}
