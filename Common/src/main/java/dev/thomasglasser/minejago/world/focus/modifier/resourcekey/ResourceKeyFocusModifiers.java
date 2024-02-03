package dev.thomasglasser.minejago.world.focus.modifier.resourcekey;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

public class ResourceKeyFocusModifiers
{
	public static final Map<String, ResourceKey<? extends Registry<?>>> ACCEPTABLE_REGISTRIES = new HashMap<>();

	private static final Map<ResourceKey<?>, List<DoubleUnaryOperator>> RESOURCE_KEY_FOCUS_MODIFIERS = new HashMap<>();

	public static void init()
	{
		ACCEPTABLE_REGISTRIES.putAll(Map.of(
				"biome", Registries.BIOME,
				"level", Registries.DIMENSION,
				"mob_effect", Registries.MOB_EFFECT,
				"structure", Registries.STRUCTURE
		));
	}

	private ResourceKeyFocusModifiers() {
		throw new UnsupportedOperationException("ResourceKeyFocusModifiers only contains static definitions.");
	}

	public static void load(ResourceManager resourceManager) {
		RESOURCE_KEY_FOCUS_MODIFIERS.clear();
		ACCEPTABLE_REGISTRIES.forEach((loc, key) ->
				resourceManager.listResources(Minejago.MOD_ID + "/focus_modifiers/" + loc, (path) -> path.getPath().endsWith(".json"))
						.forEach((location, resource) -> load(location, resource, key))
		);
	}

	private static <T> void load(ResourceLocation resourceId, Resource resource, ResourceKey<? extends Registry<?>> registry) {
		ResourceLocation id = new ResourceLocation(resourceId.getNamespace(), resourceId.getPath().replace(".json", ""));

		try {
			InputStreamReader reader = new InputStreamReader(resource.open());

			try {
				JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
				ResourceKeyFocusModifier.fromJson(id, json, (ResourceKey<? extends Registry<T>>) registry).ifPresent(modifier -> {
					ResourceKey<T> key = modifier.getKey();
					List<DoubleUnaryOperator> data = RESOURCE_KEY_FOCUS_MODIFIERS.getOrDefault(key, new ArrayList<>());
					data.add(modifier::apply);
					RESOURCE_KEY_FOCUS_MODIFIERS.put(key, data);
				});
			} catch (Throwable var7) {
				try {
					reader.close();
				} catch (Throwable var6) {
					var7.addSuppressed(var6);
				}

				throw var7;
			}

			reader.close();
		} catch (IllegalStateException | IOException var8) {
			Minejago.LOGGER.warn("Failed to load biome focus modifier \"" + id + "\".");
		}

	}

	public static double applyModifier(ResourceKey<?> key, double oldValue) {
		List<DoubleUnaryOperator> data = RESOURCE_KEY_FOCUS_MODIFIERS.get(key);
		if (!data.isEmpty())
		{
			double newValue = oldValue;
			for (DoubleUnaryOperator operation : data)
			{
				newValue = operation.applyAsDouble(oldValue);
			}
			return newValue;
		}
		return oldValue;
	}
}