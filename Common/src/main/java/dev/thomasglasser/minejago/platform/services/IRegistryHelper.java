package dev.thomasglasser.minejago.platform.services;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public interface IRegistryHelper
{
    public <A> Supplier<Registry<A>> createRegistry(String name, ResourceKey<Registry<A>> registry, Class<A> type, Supplier<A> def);
}
