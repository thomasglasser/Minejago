package dev.thomasglasser.minejago.world.focus.modifier.effect;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MobEffectFocusModifier extends FocusModifier
{
	private final MobEffect mobEffect;

	public MobEffectFocusModifier(ResourceLocation id, MobEffect mobEffect, double modifier, Operation operation)
	{
		super(id, modifier, operation);
		this.mobEffect = mobEffect;
	}

	public MobEffect getMobEffect()
	{
		return mobEffect;
	}

	public String toString() {
		return "MobEffectFocusModifier{id=" + getId() + "effect=" + mobEffect + "}";
	}

	public static @NotNull Optional<MobEffectFocusModifier> fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
		if (json.has("effect") && json.has("modifier")) {
			MobEffect effect = BuiltInRegistries.MOB_EFFECT.byNameCodec().parse(JsonOps.INSTANCE, json.get("effect")).result().orElse(null);
			if (effect == null) {
				return Optional.empty();
			} else {
				Operation operation = Operation.ADDITION;
				if (json.has("operation"))
				{
					operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
				}
				JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
				if (modifierElement.isNumber()) {
					return Optional.of(new MobEffectFocusModifier(id, effect, modifierElement.getAsDouble(), operation));
				} else {
					Minejago.LOGGER.warn("Failed to parse mob effect focus modifier \"" + id + "\", invalid format: \"modifier\" field value isn't number.");
					return Optional.empty();
				}
			}
		} else {
			Minejago.LOGGER.warn("Failed to parse mob effect focus modifier \"" + id + "\", invalid format: missing required fields.");
			return Optional.empty();
		}
	}

	@Override
	public JsonObject toJson()
	{
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("effect", BuiltInRegistries.MOB_EFFECT.byNameCodec().encodeStart(JsonOps.INSTANCE, mobEffect).get().orThrow());
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
		return "mob_effect";
	}
}
