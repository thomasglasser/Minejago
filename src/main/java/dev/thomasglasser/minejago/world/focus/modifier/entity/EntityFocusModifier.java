package dev.thomasglasser.minejago.world.focus.modifier.entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class EntityFocusModifier extends FocusModifier {
    private final EntityType<?> entityType;
    private final CompoundTag nbt;

    public EntityFocusModifier(ResourceLocation id, EntityType<?> entityType, CompoundTag nbt, double modifier, Operation operation) {
        super(id, modifier, operation);
        this.entityType = entityType;
        this.nbt = nbt;
    }

    public EntityType<?> getEntityType() {
        return this.entityType;
    }

    public CompoundTag getNbt() {
        return nbt;
    }

    public String toString() {
        return "EntityFocusModifier{id=" + getId() + "entityType=" + entityType + "nbt=" + nbt + "}";
    }

    public static Optional<EntityFocusModifier> fromJson(ResourceLocation id, JsonObject json) {
        if (json.has("entity_type") && json.has("modifier")) {
            ResourceLocation entityTypeLoc = ResourceLocation.CODEC.parse(JsonOps.INSTANCE, json.get("entity_type")).result().orElse(ResourceLocation.withDefaultNamespace(""));
            if (!BuiltInRegistries.ENTITY_TYPE.containsKey(entityTypeLoc)) {
                return Optional.empty();
            } else {
                CompoundTag nbt = new CompoundTag();
                if (json.has("nbt")) {
                    nbt = CompoundTag.CODEC.parse(JsonOps.INSTANCE, json.get("nbt")).result().orElse(new CompoundTag());
                }
                Operation operation = Operation.ADDITION;
                if (json.has("operation")) {
                    operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
                }
                JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
                if (modifierElement.isNumber()) {
                    return Optional.of(new EntityFocusModifier(id, BuiltInRegistries.ENTITY_TYPE.get(entityTypeLoc), nbt, modifierElement.getAsDouble(), operation));
                } else {
                    Minejago.LOGGER.warn("Failed to parse entity focus modifier \"" + id + "\", invalid format: \"modifier\" field value isn't number.");
                    return Optional.empty();
                }
            }
        } else {
            Minejago.LOGGER.warn("Failed to parse entity focus modifier \"" + id + "\", invalid format: missing required fields.");
            return Optional.empty();
        }
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("entity_type", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
        jsonObject.add("nbt", CompoundTag.CODEC.encodeStart(JsonOps.INSTANCE, nbt).getOrThrow());
        JsonObject info = super.toJson();
        for (String s : info.keySet()) {
            jsonObject.add(s, info.get(s));
        }
        return jsonObject;
    }

    @Override
    public String getType() {
        return "entity_type";
    }
}
