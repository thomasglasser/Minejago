package dev.thomasglasser.minejago.world.focus.modifier.blockstate;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlockStateFocusModifier extends FocusModifier {
    @Nullable
    private final BlockState state;
    @Nullable
    private final Block block;

    public BlockStateFocusModifier(ResourceLocation id, @Nullable BlockState state, @Nullable Block block, double modifier, Operation operation) {
        super(id, modifier, operation);
        this.state = state;
        this.block = block;
    }

    public @Nullable BlockState getState() {
        return this.state;
    }

    public @Nullable Block getBlock() {
        return block;
    }

    public String toString() {
        return "BlockStateFocusModifier{id=" + getId() + "state=" + state + "block=" + block + "}";
    }

    public static Optional<BlockStateFocusModifier> fromJson(ResourceLocation id, JsonObject json) {
        if (json.has("modifier")) {
            BlockState state = null;
            Block block = null;
            if (json.has("state"))
                state = BlockState.CODEC.parse(JsonOps.INSTANCE, json.get("state")).result().orElse(null);
            else if (json.has("block")) {
                ResourceLocation loc = ResourceLocation.CODEC.parse(JsonOps.INSTANCE, json.get("block")).result().orElse(ResourceLocation.withDefaultNamespace(""));
                if (BuiltInRegistries.BLOCK.containsKey(loc))
                    block = BuiltInRegistries.BLOCK.get(loc);
                else {
                    Minejago.LOGGER.warn("Failed to parse block state focus modifier \"" + id + "\", invalid format: block not found.");
                    return Optional.empty();
                }
            }
            if (state == null && block == null) {
                return Optional.empty();
            } else {
                Operation operation = Operation.ADDITION;
                if (json.has("operation")) {
                    operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
                }
                JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
                if (modifierElement.isNumber()) {
                    return Optional.of(new BlockStateFocusModifier(id, state, block, modifierElement.getAsDouble(), operation));
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
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        if (state != null)
            jsonObject.add("state", BlockState.CODEC.encodeStart(JsonOps.INSTANCE, state).getOrThrow());
        else if (block != null)
            jsonObject.addProperty("block", BuiltInRegistries.BLOCK.getKey(block).toString());
        else
            throw new IllegalStateException("BlockStateFocusModifier must have either a state or block.");
        JsonObject info = super.toJson();
        for (String s : info.keySet()) {
            jsonObject.add(s, info.get(s));
        }
        return jsonObject;
    }

    @Override
    public String getType() {
        return "block";
    }
}
