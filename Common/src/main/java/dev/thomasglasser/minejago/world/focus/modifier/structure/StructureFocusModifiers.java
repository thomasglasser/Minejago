package dev.thomasglasser.minejago.world.focus.modifier.structure;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StructureFocusModifiers
{
	private static final List<StructureFocusModifier> STRUCTURE_FOCUS_MODIFIERS = new ArrayList<>();

	private StructureFocusModifiers() {
		throw new UnsupportedOperationException("StructureFocusModifiers only contains static definitions.");
	}

	public static void load(ResourceManager resourceManager) {
		STRUCTURE_FOCUS_MODIFIERS.clear();
		resourceManager.listResources(Minejago.MOD_ID + "/focus_modifiers/structure", (path) -> path.getPath().endsWith(".json"))
				.forEach(StructureFocusModifiers::load);
	}

	private static void load(ResourceLocation resourceId, Resource resource) {
		ResourceLocation id = new ResourceLocation(resourceId.getNamespace(), resourceId.getPath().replace(".json", ""));

		try {
			InputStreamReader reader = new InputStreamReader(resource.open());

			try {
				JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
				StructureFocusModifier.fromJson(id, json).ifPresent(STRUCTURE_FOCUS_MODIFIERS::add);
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
			Minejago.LOGGER.warn("Failed to load structure focus modifier \"" + id + "\".");
		}

	}

	public static double applyModifier(ResourceKey<Structure> structure, double oldValue) {
		List<StructureFocusModifier> data = STRUCTURE_FOCUS_MODIFIERS.stream().filter(modifier -> modifier.getStructure().equals(structure)).toList();
		if (!data.isEmpty())
		{
			double newValue = oldValue;
			for (StructureFocusModifier modifier : data)
			{
				newValue = modifier.apply(newValue);
			}
			return newValue;
		}
		return oldValue;
	}
}