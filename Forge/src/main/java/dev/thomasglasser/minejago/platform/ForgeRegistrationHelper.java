package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.RegistrationHelper;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ForgeRegistrationHelper implements RegistrationHelper
{
	public static final Map<Registry<?>, DeferredRegister<?>> DEFERRED_REGISTERS = new HashMap<>();

	@Override
	public <V, T extends V> Supplier<T> register(Registry<V> registry, String name, Supplier<T> supplier)
	{
		return getOrCreateDeferredRegister(registry).register(name, supplier);
	}

	private <T> DeferredRegister<T> getOrCreateDeferredRegister(Registry<T> registry)
	{
		if (DEFERRED_REGISTERS.containsKey(registry))
			return (DeferredRegister<T>) DEFERRED_REGISTERS.get(registry);
		else
		{
			DeferredRegister<T> deferredRegister = DeferredRegister.create(registry, Minejago.MOD_ID);
        	DEFERRED_REGISTERS.put(registry, deferredRegister);
        	return deferredRegister;
		}
	}
}
