package dev.thomasglasser.minejago.world.focus.modifier.world;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WorldFocusModifier extends FocusModifier
{
	@Nullable
	private final IntProvider dayTime;
	@Nullable
	private final Weather weather;
	@Nullable
	private final IntProvider y;
	@Nullable
	private final IntProvider temperature;

	protected WorldFocusModifier(ResourceLocation id, @Nullable IntProvider dayTime, @Nullable Weather weather, @Nullable IntProvider y, @Nullable IntProvider temperature, double modifier, Operation operation) {
		super(id, modifier, operation);
		this.dayTime = dayTime;
		this.weather = weather;
		this.y = y;
		this.temperature = temperature;
	}

	public @Nullable IntProvider getDayTime()
	{
		return dayTime;
	}

	@Nullable
	public Weather getWeather()
	{
		return weather;
	}

	@Nullable
	public IntProvider getY()
	{
		return y;
	}

	@Nullable
	public IntProvider getTemperature()
	{
		return temperature;
	}

	public String toString() {
		return "WorldFocusModifier{id=" + getId() + "day_time=" + dayTime + "weather=" + weather + "y=" + y + "temperature=" + temperature + "}";
	}

	public static @NotNull Optional<WorldFocusModifier> fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
		if ((json.has("day_time") || json.has("weather") || json.has("y") || json.has("temperature")) && json.has("modifier")) {
			IntProvider dayTime = null;
			Weather weather = null;
			IntProvider y = null;
			IntProvider temperature = null;
			if (json.has("day_time"))
				dayTime = IntProvider.CODEC.parse(JsonOps.INSTANCE, json.get("day_time")).result().orElse(null);
			if (json.has("weather"))
				weather = Codec.STRING.parse(JsonOps.INSTANCE, json.get("weather")).map(Weather::of).result().orElse(null);
			if (json.has("y"))
				y = IntProvider.CODEC.parse(JsonOps.INSTANCE, json.get("y")).result().orElse(null);
			if (json.has("temperature"))
				temperature = IntProvider.CODEC.parse(JsonOps.INSTANCE, json.get("temperature")).result().orElse(null);
			Operation operation = Operation.ADDITION;
			if (json.has("operation"))
			{
				operation = Codec.STRING.parse(JsonOps.INSTANCE, json.get("operation")).map(Operation::of).result().orElse(Operation.ADDITION);
			}
			JsonPrimitive modifierElement = json.get("modifier").getAsJsonPrimitive();
			if (modifierElement.isNumber()) {
				return Optional.of(new WorldFocusModifier(id, dayTime, weather, y, temperature, modifierElement.getAsDouble(), operation));
			} else {
				Minejago.LOGGER.warn("Failed to parse world focus modifier \"" + id + "\", invalid format: \"modifier\" field value isn't number.");
				return Optional.empty();
			}
		} else {
			Minejago.LOGGER.warn("Failed to parse world focus modifier \"" + id + "\", invalid format: missing required fields.");
			return Optional.empty();
		}
	}

	@Override
	public JsonObject toJson()
	{
		JsonObject jsonObject = new JsonObject();
		if (dayTime != null) jsonObject.add("day_time", IntProvider.CODEC.encodeStart(JsonOps.INSTANCE, dayTime).getOrThrow());
		if (weather != null) jsonObject.addProperty("weather", weather.toString().toLowerCase());
		if (y != null) jsonObject.add("y", IntProvider.CODEC.encodeStart(JsonOps.INSTANCE, y).getOrThrow());
		if (temperature != null) jsonObject.add("temperature", IntProvider.CODEC.encodeStart(JsonOps.INSTANCE, temperature).getOrThrow());
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
		return "world";
	}

	public static class Builder
	{
		private final ResourceLocation id;
		private final double modifier;
		private final Operation operation;
		private IntProvider dayTime;
		@Nullable
		private Weather weather = null;
		@Nullable
		private IntProvider y = null;
		@Nullable
		private IntProvider temperature = null;

		public Builder(ResourceLocation id, double modifier, Operation operation)
		{
			this.id = id;
			this.modifier = modifier;
			this.operation = operation;
		}

		public static Builder of(ResourceLocation id, double modifier, Operation operation)
		{
			return new Builder(id, modifier, operation);
		}

		public Builder dayTime(IntProvider dayTime)
		{
			this.dayTime = dayTime;
			return this;
		}

		public Builder weather(Weather weather)
		{
			this.weather = weather;
			return this;
		}

		public Builder y(IntProvider y)
		{
			this.y = y;
			return this;
		}

		public Builder temperature(IntProvider temperature)
		{
			this.temperature = temperature;
			return this;
		}

		public WorldFocusModifier build()
		{
			return new WorldFocusModifier(id, dayTime, weather, y, temperature, modifier, operation);
		}
	}
}
