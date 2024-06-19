package dev.thomasglasser.minejago.world.focus.modifier;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public abstract class FocusModifier
{
	private final ResourceLocation id;
	private final double modifier;
	private final Operation operation;

	public FocusModifier(ResourceLocation id, double modifier, Operation operation) {
		this.id = id;
		this.modifier = modifier;
		this.operation = operation;
	}

	public ResourceLocation getId() {
		return this.id;
	}

	public double getModifier()
	{
		return modifier;
	}

	public Operation getOperation()
	{
		return operation;
	}

	public double apply(double oldValue)
	{
		return operation.calculate(oldValue, modifier);
	}

	public String toString() {
		return "FocusModifier{id=" + id + "}";
	}

	public JsonObject toJson()
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("modifier", modifier);
		jsonObject.addProperty("operation", operation.toString().toLowerCase());
		return jsonObject;
	}

	public abstract String getType();
}
