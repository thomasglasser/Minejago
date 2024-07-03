package dev.thomasglasser.minejago.world.focus.modifier.resourcekey;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class ResourceKeyFocusModifier<T> extends FocusModifier {
    private final ResourceKey<T> key;

    public ResourceKeyFocusModifier(ResourceLocation id, ResourceKey<T> biome, double modifier, Operation operation) {
        super(id, modifier, operation);
        this.key = biome;
    }

    public ResourceKey<T> getKey() {
        return this.key;
    }

    public String toString() {
        return "ResourceKeyFocusModifier{id=" + getId() + "key=" + key + "}";
    }

    public static <T> Optional<ResourceKeyFocusModifier<T>> fromJson(ResourceLocation id, JsonObject json, ResourceKey<? extends Registry<T>> registry) {
        if (json.has("key") && json.has("modifier")) {
            ResourceKey<T> key = ResourceKey.codec(registry).parse(JsonOps.INSTANCE, json.get("key")).getOrThrow();
            if (key == null) {
                return Optional.empty();
            } else {
                Operation operation = Operation.ADDITION;
                if (json.has("operation")) {
                    operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
                }
                JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
                if (modifierElement.isNumber()) {
                    return Optional.of(new ResourceKeyFocusModifier<>(id, key, modifierElement.getAsDouble(), operation));
                } else {
                    Minejago.LOGGER.warn("Failed to parse key focus modifier \"" + id + "\", invalid format: \"modifier\" field value isn't number.");
                    return Optional.empty();
                }
            }
        } else {
            Minejago.LOGGER.warn("Failed to parse key focus modifier \"" + id + "\", invalid format: missing required fields.");
            return Optional.empty();
        }
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("key", ResourceKey.codec(ResourceKey.createRegistryKey(key.registry())).encodeStart(JsonOps.INSTANCE, (ResourceKey<Object>) key).getOrThrow());
        JsonObject info = super.toJson();
        for (String s : info.keySet()) {
            jsonObject.add(s, info.get(s));
        }
        return jsonObject;
    }

    @Override
    public String getType() {
        return key.registry().getPath();
    }
}
