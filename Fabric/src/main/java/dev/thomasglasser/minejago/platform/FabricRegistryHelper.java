package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.IRegistryHelper;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper
{
    @Override
    public <A> Supplier<Registry<A>> createRegistry(String name, ResourceKey<Registry<A>> registry, Class<A> type, Supplier<A> def) {
        return () -> FabricRegistryBuilder.createSimple(type, Minejago.modLoc(name)).buildAndRegister();
    }
}
