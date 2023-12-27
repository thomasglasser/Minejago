package dev.thomasglasser.minejago.world.focus.modifier.dimension;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DimensionFocusModifier extends FocusModifier
{
	private final ResourceKey<Level> dimension;

	public DimensionFocusModifier(ResourceLocation id, ResourceKey<Level> dimension, double modifier, Operation operation) {
		super(id, modifier, operation);
		this.dimension = dimension;
	}

	public ResourceKey<Level> getDimension()
	{
		return dimension;
	}

	public String toString() {
		return "DimensionFocusModifier{id=" + getId() + "dimension=" + dimension + "}";
	}

	public static @NotNull Optional<DimensionFocusModifier> fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
		if (json.has("dimension") && json.has("modifier")) {
			ResourceKey<Level> dimension = ResourceKey.codec(Registries.DIMENSION).parse(JsonOps.INSTANCE, json.get("dimension")).get().orThrow();
			if (dimension == null) {
				return Optional.empty();
			} else {
				Operation operation = Operation.ADDITION;
				if (json.has("operation"))
				{
					operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
				}
				JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
				if (modifierElement.isNumber()) {
					return Optional.of(new DimensionFocusModifier(id, dimension, modifierElement.getAsDouble(), operation));
				} else {
					Minejago.LOGGER.warn("Failed to parse dimension focus modifier \"" + id + "\", invalid format: \"modifier\" field value isn't number.");
					return Optional.empty();
				}
			}
		} else {
			Minejago.LOGGER.warn("Failed to parse biome focus modifier \"" + id + "\", invalid format: missing required fields.");
			return Optional.empty();
		}
	}

	@Override
	public JsonObject toJson()
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("dimension", ResourceKey.codec(Registries.DIMENSION).encodeStart(JsonOps.INSTANCE, dimension).get().orThrow());
		JsonObject info = super.toJson();
		for (String s : info.keySet())
		{
			jsonObject.add(s, info.get(s));
		}
		return jsonObject;
	}

	@Override
	public String getType()
	{
		return "dimension";
	}
}
