package dev.thomasglasser.minejago.world.focus.modifier.structure;

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
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class StructureFocusModifier extends FocusModifier
{
	private final ResourceKey<Structure> structure;

	public StructureFocusModifier(ResourceLocation id, ResourceKey<Structure> structure, double modifier, Operation operation) {
		super(id, modifier, operation);
		this.structure = structure;
	}

	public ResourceKey<Structure> getStructure() {
		return this.structure;
	}

	public String toString() {
		return "StructureFocusModifier{id=" + getId() + "structure=" + structure + "}";
	}

	public static @NotNull Optional<StructureFocusModifier> fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
		if (json.has("structure") && json.has("modifier")) {
			ResourceKey<Structure> structure = ResourceKey.codec(Registries.STRUCTURE).parse(JsonOps.INSTANCE, json.get("structure")).get().orThrow();
			if (structure == null) {
				return Optional.empty();
			} else {
				Operation operation = Operation.ADDITION;
				if (json.has("operation"))
				{
					operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
				}
				JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
				if (modifierElement.isNumber()) {
					return Optional.of(new StructureFocusModifier(id, structure, modifierElement.getAsDouble(), operation));
				} else {
					Minejago.LOGGER.warn("Failed to parse item structure focus modifier \"" + id + "\", invalid format: \"modifier\" field value isn't number.");
					return Optional.empty();
				}
			}
		} else {
			Minejago.LOGGER.warn("Failed to parse item structure focus modifier \"" + id + "\", invalid format: missing required fields.");
			return Optional.empty();
		}
	}

	@Override
	public JsonObject toJson()
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("structure", ResourceKey.codec(Registries.STRUCTURE).encodeStart(JsonOps.INSTANCE, structure).get().orThrow());
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
		return "structure";
	}
}
