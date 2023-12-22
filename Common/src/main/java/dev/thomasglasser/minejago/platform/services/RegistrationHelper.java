package dev.thomasglasser.minejago.platform.services;

import net.minecraft.core.Registry;

import java.util.function.Supplier;

public interface RegistrationHelper
{
	<V, T extends V> Supplier<T> register(Registry<V> registry, String name, Supplier<T> supplier);
}
