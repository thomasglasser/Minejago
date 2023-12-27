package dev.thomasglasser.minejago.world.focus.modifier.blockstate;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BlockStateFocusModifier extends FocusModifier
{
	private final BlockState state;

	public BlockStateFocusModifier(ResourceLocation id, BlockState state, double modifier, Operation operation) {
		super(id, modifier, operation);
		this.state = state;
	}

	public BlockState getState() {
		return this.state;
	}

	public String toString() {
		return "BlockStateFocusModifier{id=" + getId() + "state=" + state + "}";
	}

	public static @NotNull Optional<BlockStateFocusModifier> fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
		if (json.has("modifier")) {
			BlockState state = null;
			if (json.has("state"))
				state = BlockState.CODEC.parse(JsonOps.INSTANCE, json.get("state")).result().orElse(null);
			else if (json.has("block"))
			{
				ResourceLocation loc = ResourceLocation.CODEC.parse(JsonOps.INSTANCE, json.get("block")).result().orElse(new ResourceLocation(""));
				if (BuiltInRegistries.BLOCK.containsKey(loc))
					state = BuiltInRegistries.BLOCK.get(loc).defaultBlockState();
				else
				{
					Minejago.LOGGER.warn("Failed to parse block state focus modifier \"" + id + "\", invalid format: block not found.");
					return Optional.empty();
				}
			}
			if (state == null) {
				return Optional.empty();
			} else {
				Operation operation = Operation.ADDITION;
				if (json.has("operation"))
				{
					operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
				}
				JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
				if (modifierElement.isNumber()) {
					return Optional.of(new BlockStateFocusModifier(id, state, modifierElement.getAsDouble(), operation));
				} else {
					Minejago.LOGGER.warn("Failed to parse block state focus modifier \"" + id + "\", invalid format: \"modifier\" field value isn't number.");
					return Optional.empty();
				}
			}
		} else {
			Minejago.LOGGER.warn("Failed to parse block state focus modifier \"" + id + "\", invalid format: missing required fields.");
			return Optional.empty();
		}
	}

	@Override
	public JsonObject toJson()
	{
		JsonObject jsonObject = new JsonObject();
		if (state == state.getBlock().defaultBlockState())
			jsonObject.addProperty("block", BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString());
		else
			jsonObject.add("state", BlockState.CODEC.encodeStart(JsonOps.INSTANCE, state).get().orThrow());
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
		return "block";
	}
}
