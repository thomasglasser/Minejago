package dev.thomasglasser.minejago.world.focus.modifier.biome;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.biome.Biome;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BiomeFocusModifiers
{
	private static final List<BiomeFocusModifier> BIOME_FOCUS_MODIFIERS = new ArrayList<>();

	private BiomeFocusModifiers() {
		throw new UnsupportedOperationException("BiomeFocusModifiers only contains static definitions.");
	}

	public static void load(ResourceManager resourceManager) {
		BIOME_FOCUS_MODIFIERS.clear();
		resourceManager.listResources(Minejago.MOD_ID + "/focus_modifiers/biome", (path) -> path.getPath().endsWith(".json"))
				.forEach(BiomeFocusModifiers::load);
	}

	private static void load(ResourceLocation resourceId, Resource resource) {
		ResourceLocation id = new ResourceLocation(resourceId.getNamespace(), resourceId.getPath().replace(".json", ""));

		try {
			InputStreamReader reader = new InputStreamReader(resource.open());

			try {
				JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
				BiomeFocusModifier.fromJson(id, json).ifPresent(BIOME_FOCUS_MODIFIERS::add);
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

	public static double applyModifier(ResourceKey<Biome> biome, double oldValue) {
		List<BiomeFocusModifier> data = BIOME_FOCUS_MODIFIERS.stream().filter(modifier -> modifier.getBiome().equals(biome)).toList();
		if (!data.isEmpty())
		{
			double newValue = oldValue;
			for (BiomeFocusModifier modifier : data)
			{
				newValue = modifier.apply(newValue);
			}
			return newValue;
		}
		return oldValue;
	}
}