package dev.thomasglasser.minejago.world.focus.modifier.biome;

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
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BiomeFocusModifier extends FocusModifier
{
	private final ResourceKey<Biome> biome;

	public BiomeFocusModifier(ResourceLocation id, ResourceKey<Biome> biome, double modifier, Operation operation) {
		super(id, modifier, operation);
		this.biome = biome;
	}

	public ResourceKey<Biome> getBiome() {
		return this.biome;
	}

	public String toString() {
		return "BiomeFocusModifier{id=" + getId() + "biome=" + biome + "}";
	}

	public static @NotNull Optional<BiomeFocusModifier> fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
		if (json.has("biome") && json.has("modifier")) {
			ResourceKey<Biome> biome = ResourceKey.codec(Registries.BIOME).parse(JsonOps.INSTANCE, json.get("biome")).get().orThrow();
			if (biome == null) {
				return Optional.empty();
			} else {
				Operation operation = Operation.ADDITION;
				if (json.has("operation"))
				{
					operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
				}
				JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
				if (modifierElement.isNumber()) {
					return Optional.of(new BiomeFocusModifier(id, biome, modifierElement.getAsDouble(), operation));
				} else {
					Minejago.LOGGER.warn("Failed to parse biome focus modifier \"" + id + "\", invalid format: \"modifier\" field value isn't number.");
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
		jsonObject.add("biome", ResourceKey.codec(Registries.BIOME).encodeStart(JsonOps.INSTANCE, biome).get().orThrow());
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
		return "biome";
	}
}
