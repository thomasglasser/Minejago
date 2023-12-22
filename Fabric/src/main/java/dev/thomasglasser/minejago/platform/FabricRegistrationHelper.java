package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.RegistrationHelper;
import net.minecraft.core.Registry;

import java.util.function.Supplier;

public class FabricRegistrationHelper implements RegistrationHelper
{
	@Override
	public <V, T extends V> Supplier<T> register(Registry<V> registry, String name, Supplier<T> supplier)
	{
		T o = Registry.register(registry, name, supplier.get());
		return () -> o;
	}
}
