package dev.thomasglasser.minejago.world.focus.modifier.blockstate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockStateFocusModifiers
{
	private static final List<BlockStateFocusModifier> BLOCK_STATE_FOCUS_MODIFIERS = new ArrayList<>();

	private BlockStateFocusModifiers() {
		throw new UnsupportedOperationException("BlockStateFocusModifiers only contains static definitions.");
	}

	public static void load(ResourceManager resourceManager) {
		BLOCK_STATE_FOCUS_MODIFIERS.clear();
		resourceManager.listResources(Minejago.MOD_ID + "/focus_modifiers/block", (path) -> path.getPath().endsWith(".json"))
				.forEach(BlockStateFocusModifiers::load);
	}

	private static void load(ResourceLocation resourceId, Resource resource) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(resourceId.getNamespace(), resourceId.getPath().replace(".json", ""));

		try {
			InputStreamReader reader = new InputStreamReader(resource.open());

			try {
				JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
				BlockStateFocusModifier.fromJson(id, json).ifPresent(BLOCK_STATE_FOCUS_MODIFIERS::add);
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
			Minejago.LOGGER.warn("Failed to load block state focus modifier \"" + id + "\".");
		}

	}

	public static double applyModifier(BlockState state, double oldValue) {
		List<BlockStateFocusModifier> data = new ArrayList<>(BLOCK_STATE_FOCUS_MODIFIERS.stream().filter(modifier -> modifier.getState().getBlock() == state.getBlock()).toList());
		List<BlockStateFocusModifier> matches = new ArrayList<>(data);
		if (!data.isEmpty())
		{
			for (BlockStateFocusModifier modifier : data)
			{
				for (Map.Entry<Property<?>, Comparable<?>> entry : modifier.getState().getValues().entrySet())
				{
					Property<?> property = entry.getKey();
					Comparable<?> comparable = entry.getValue();
					if (!state.getValue(property).equals(comparable))
						matches.remove(modifier);
				}
			}

			if (!matches.isEmpty())
			{
				double newValue = oldValue;
				for (BlockStateFocusModifier modifier : matches)
				{
					newValue = modifier.apply(newValue);
				}
				return newValue;
			}
		}
		return oldValue;
	}
}