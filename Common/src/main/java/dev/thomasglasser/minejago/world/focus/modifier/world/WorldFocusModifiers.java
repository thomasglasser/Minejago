package dev.thomasglasser.minejago.world.focus.modifier.world;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WorldFocusModifiers
{
	private static final List<WorldFocusModifier> WORLD_FOCUS_MODIFIERS = new ArrayList<>();

	private WorldFocusModifiers() {
		throw new UnsupportedOperationException("WorldFocusModifiers only contains static definitions.");
	}

	public static void load(ResourceManager resourceManager) {
		WORLD_FOCUS_MODIFIERS.clear();
		resourceManager.listResources(Minejago.MOD_ID + "/focus_modifiers/world", (path) -> path.getPath().endsWith(".json"))
				.forEach(WorldFocusModifiers::load);
	}

	private static void load(ResourceLocation resourceId, Resource resource) {
		ResourceLocation id = new ResourceLocation(resourceId.getNamespace(), resourceId.getPath().replace(".json", ""));

		try {
			InputStreamReader reader = new InputStreamReader(resource.open());

			try {
				JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
				WorldFocusModifier.fromJson(id, json).ifPresent(WORLD_FOCUS_MODIFIERS::add);
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
			Minejago.LOGGER.warn("Failed to load world focus modifier \"" + id + "\".");
		}

	}

	public static double applyModifier(int dayTime, Weather weather, int y, int temperature, double oldValue) {
		dayTime %= 24000;
		List<WorldFocusModifier> data = new ArrayList<>(WORLD_FOCUS_MODIFIERS.stream().toList());
		List<WorldFocusModifier> matches = new ArrayList<>(data);
		if (!data.isEmpty())
		{
			for (WorldFocusModifier modifier : data)
			{
				if (modifier.getDayTime() != null && !((modifier.getDayTime().getMinValue() % 24000) < dayTime && (modifier.getDayTime().getMaxValue() % 24000) > dayTime))
					matches.remove(modifier);
				if (modifier.getWeather() != null && modifier.getWeather() != weather)
					matches.remove(modifier);
				if (modifier.getY() != null && !(modifier.getY().getMinValue() < y && modifier.getY().getMaxValue() > y))
					matches.remove(modifier);
				if (modifier.getTemperature() != null && !(modifier.getTemperature().getMinValue() < temperature && modifier.getTemperature().getMaxValue() > temperature))
					matches.remove(modifier);
			}

			if (!matches.isEmpty())
			{
				double newValue = oldValue;
				for (WorldFocusModifier modifier : matches)
				{
					newValue = modifier.apply(newValue);
				}
				return newValue;
			}
		}
		return oldValue;
	}
}
