package dev.thomasglasser.minejago.world.focus.modifier.effect;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.effect.MobEffect;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MobEffectFocusModifiers
{
	private static final List<MobEffectFocusModifier> MOB_EFFECT_FOCUS_MODIFIERS = new ArrayList<>();

	private MobEffectFocusModifiers() {
		throw new UnsupportedOperationException("MobEffectFocusModifiers only contains static definitions.");
	}

	public static void load(ResourceManager resourceManager) {
		MOB_EFFECT_FOCUS_MODIFIERS.clear();
		resourceManager.listResources(Minejago.MOD_ID + "/focus_modifiers/mob_effect", (path) -> path.getPath().endsWith(".json"))
				.forEach(MobEffectFocusModifiers::load);
	}

	private static void load(ResourceLocation resourceId, Resource resource) {
		ResourceLocation id = new ResourceLocation(resourceId.getNamespace(), resourceId.getPath().replace(".json", ""));

		try {
			InputStreamReader reader = new InputStreamReader(resource.open());

			try {
				JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
				MobEffectFocusModifier.fromJson(id, json).ifPresent(MOB_EFFECT_FOCUS_MODIFIERS::add);
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
			Minejago.LOGGER.warn("Failed to load mob effect focus modifier \"" + id + "\".");
		}

	}

	public static double applyModifier(MobEffect mobEffect, double oldValue) {
		List<MobEffectFocusModifier> data = MOB_EFFECT_FOCUS_MODIFIERS.stream().filter(modifier -> modifier.getMobEffect().equals(mobEffect)).toList();
		if (!data.isEmpty())
		{
			double newValue = oldValue;
			for (MobEffectFocusModifier modifier : data)
			{
				newValue = modifier.apply(newValue);
			}
			return newValue;
		}
		return oldValue;
	}
}