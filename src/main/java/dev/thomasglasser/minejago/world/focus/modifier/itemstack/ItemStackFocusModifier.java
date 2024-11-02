package dev.thomasglasser.minejago.world.focus.modifier.itemstack;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemStackFocusModifier extends FocusModifier {
    private final ItemStack stack;
    @Nullable
    private final Item item;

    public ItemStackFocusModifier(ResourceLocation id, ItemStack stack, @Nullable Item item, double modifier, Operation operation) {
        super(id, modifier, operation);
        this.stack = stack;
        this.item = item;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public @Nullable Item getItem() {
        return this.item;
    }

    public String toString() {
        return "ItemStackFocusModifier{id=" + getId() + "stack=" + stack + "item=" + item + "}";
    }

    public static Optional<ItemStackFocusModifier> fromJson(ResourceLocation id, JsonObject json) {
        if (json.has("modifier")) {
            ItemStack stack = ItemStack.EMPTY;
            Item item = null;
            if (json.has("stack"))
                stack = ItemStack.CODEC.parse(JsonOps.INSTANCE, json.get("stack")).result().orElse(ItemStack.EMPTY);
            else if (json.has("item")) {
                ResourceLocation loc = ResourceLocation.CODEC.parse(JsonOps.INSTANCE, json.get("item")).result().orElse(ResourceLocation.withDefaultNamespace(""));
                if (BuiltInRegistries.ITEM.containsKey(loc))
                    item = BuiltInRegistries.ITEM.getValue(loc);
                else {
                    Minejago.LOGGER.warn("Failed to parse item stack focus modifier \"" + id + "\", invalid format: item not found.");
                    return Optional.empty();
                }
            }
            if (stack == ItemStack.EMPTY && item == null) {
                return Optional.empty();
            } else {
                Operation operation = Operation.ADDITION;
                if (json.has("operation")) {
                    operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
                }
                JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
                if (modifierElement.isNumber()) {
                    return Optional.of(new ItemStackFocusModifier(id, stack, item, modifierElement.getAsDouble(), operation));
                } else {
                    Minejago.LOGGER.warn("Failed to parse item stack focus modifier \"" + id + "\", invalid format: \"modifier\" field value isn't number.");
                    return Optional.empty();
                }
            }
        } else {
            Minejago.LOGGER.warn("Failed to parse item stack focus modifier \"" + id + "\", invalid format: missing required fields.");
            return Optional.empty();
        }
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        if (!stack.isEmpty())
            jsonObject.add("stack", ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, stack).result().orElseThrow());
        else if (item != null)
            jsonObject.addProperty("item", BuiltInRegistries.ITEM.getKey(item).toString());
        else
            throw new IllegalStateException("ItemStackFocusModifier must have either a stack or an item.");
        JsonObject info = super.toJson();
        for (String s : info.keySet()) {
            jsonObject.add(s, info.get(s));
        }
        return jsonObject;
    }

    @Override
    public String getType() {
        return "item";
    }
}
