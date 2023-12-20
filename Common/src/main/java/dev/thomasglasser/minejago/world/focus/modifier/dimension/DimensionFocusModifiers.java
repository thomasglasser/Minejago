package dev.thomasglasser.minejago.world.focus.modifier.dimension;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.Level;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DimensionFocusModifiers
{
	private static final List<DimensionFocusModifier> DIMENSION_FOCUS_MODIFIERS = new ArrayList<>();

	private DimensionFocusModifiers() {
		throw new UnsupportedOperationException("DimensionFocusModifiers only contains static definitions.");
	}

	public static void load(ResourceManager resourceManager) {
		DIMENSION_FOCUS_MODIFIERS.clear();
		resourceManager.listResources(Minejago.MOD_ID + "/focus_modifiers/dimension", (path) -> path.getPath().endsWith(".json"))
				.forEach(DimensionFocusModifiers::load);
	}

	private static void load(ResourceLocation resourceId, Resource resource) {
		ResourceLocation id = new ResourceLocation(resourceId.getNamespace(), resourceId.getPath().replace(".json", ""));

		try {
			InputStreamReader reader = new InputStreamReader(resource.open());

			try {
				JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
				DimensionFocusModifier.fromJson(id, json).ifPresent(DIMENSION_FOCUS_MODIFIERS::add);
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
			Minejago.LOGGER.warn("Failed to load dimension focus modifier \"" + id + "\".");
		}

	}

	public static double applyModifier(ResourceKey<Level> dimension, double oldValue) {
		List<DimensionFocusModifier> data = DIMENSION_FOCUS_MODIFIERS.stream().filter(modifier -> modifier.getDimension().equals(dimension)).toList();
		if (!data.isEmpty())
		{
			double newValue = oldValue;
			for (DimensionFocusModifier modifier : data)
			{
				newValue = modifier.apply(newValue);
			}
			return newValue;
		}
		return oldValue;
	}
}
