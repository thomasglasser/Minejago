package dev.thomasglasser.minejago.world.focus.modifier.itemstack;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ItemStackFocusModifier extends FocusModifier
{
	private final ItemStack stack;

	public ItemStackFocusModifier(ResourceLocation id, ItemStack stack, double modifier, Operation operation) {
		super(id, modifier, operation);
		this.stack = stack;
	}

	public ItemStack getStack() {
		return this.stack;
	}

	public Item getItem()
	{
		return stack.getItem();
	}

	public String toString() {
		return "ItemStackFocusModifier{id=" + getId() + "stack=" + stack + "}";
	}

	public static Optional<ItemStackFocusModifier> fromJson(ResourceLocation id, JsonObject json) {
		if (json.has("modifier")) {
			ItemStack stack = ItemStack.EMPTY;
			if (json.has("stack"))
				stack = ItemStack.CODEC.parse(JsonOps.INSTANCE, json.get("stack")).result().orElse(ItemStack.EMPTY);
			else if (json.has("item"))
			{
				ResourceLocation loc = ResourceLocation.CODEC.parse(JsonOps.INSTANCE, json.get("item")).result().orElse(ResourceLocation.withDefaultNamespace(""));
				if (BuiltInRegistries.ITEM.containsKey(loc))
					stack = BuiltInRegistries.ITEM.get(loc).getDefaultInstance();
				else
				{
					Minejago.LOGGER.warn("Failed to parse item stack focus modifier \"" + id + "\", invalid format: item not found.");
					return Optional.empty();
				}
			}
			if (stack == ItemStack.EMPTY) {
				return Optional.empty();
			} else {
				Operation operation = Operation.ADDITION;
				if (json.has("operation"))
				{
					operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
				}
				JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
				if (modifierElement.isNumber()) {
					return Optional.of(new ItemStackFocusModifier(id, stack, modifierElement.getAsDouble(), operation));
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
	public JsonObject toJson()
	{
		JsonObject jsonObject = new JsonObject();
		if (stack.getCount() > 1 || !stack.getComponents().isEmpty())
			jsonObject.add("stack", ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, stack).result().orElseThrow());
		else
			jsonObject.addProperty("item", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
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
		return "item";
	}
}