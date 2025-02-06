package dev.thomasglasser.minejago.world.focus.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import java.util.Optional;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

public record WorldFocusModifier(Optional<MinMaxBounds.Ints> dayTime, Optional<Weather> weather, Optional<MinMaxBounds.Ints> y, Optional<MinMaxBounds.Ints> temperature, Operation operation, double modifier) implements FocusModifier {

    public static final MapCodec<WorldFocusModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            MinMaxBounds.Ints.CODEC.optionalFieldOf("day_time").forGetter(WorldFocusModifier::dayTime),
            Weather.CODEC.optionalFieldOf("weather").forGetter(WorldFocusModifier::weather),
            MinMaxBounds.Ints.CODEC.optionalFieldOf("y").forGetter(WorldFocusModifier::y),
            MinMaxBounds.Ints.CODEC.optionalFieldOf("temperature").forGetter(WorldFocusModifier::temperature),
            Operation.CODEC.fieldOf("operation").forGetter(WorldFocusModifier::operation),
            Codec.DOUBLE.fieldOf("modifier").forGetter(WorldFocusModifier::modifier)).apply(instance, WorldFocusModifier::new));

    @Override
    public MapCodec<? extends FocusModifier> codec() {
        return MinejagoFocusModifierSerializers.WORLD_FOCUS_MODIFIER.get();
    }

    public static double checkAndApply(ServerLevel level, int dayTime, Weather weather, int y, int temperature, double oldValue) {
        return FocusModifier.checkAndApply(level.holderLookup(MinejagoRegistries.FOCUS_MODIFIER).listElements().map(Holder.Reference::value).filter(modifier -> modifier instanceof WorldFocusModifier worldFocusModifier &&
                worldFocusModifier.dayTime.map(bounds -> bounds.matches(dayTime)).orElse(true) &&
                worldFocusModifier.weather.map(w -> w == weather).orElse(true) &&
                worldFocusModifier.y.map(bounds -> bounds.matches(y)).orElse(true) &&
                worldFocusModifier.temperature.map(bounds -> bounds.matches(temperature)).orElse(true)).toList(), oldValue);
    }
    public static class Builder {
        private final double modifier;
        private final Operation operation;
        private MinMaxBounds.Ints dayTime;
        @Nullable
        private Weather weather = null;
        @Nullable
        private MinMaxBounds.Ints y = null;
        @Nullable
        private MinMaxBounds.Ints temperature = null;

        protected Builder(double modifier, Operation operation) {
            this.modifier = modifier;
            this.operation = operation;
        }

        public static Builder of(double modifier, Operation operation) {
            return new Builder(modifier, operation);
        }

        public Builder dayTime(MinMaxBounds.Ints dayTime) {
            this.dayTime = dayTime;
            return this;
        }

        public Builder weather(Weather weather) {
            this.weather = weather;
            return this;
        }

        public Builder y(MinMaxBounds.Ints y) {
            this.y = y;
            return this;
        }

        public Builder temperature(MinMaxBounds.Ints temperature) {
            this.temperature = temperature;
            return this;
        }

        public WorldFocusModifier build() {
            return new WorldFocusModifier(Optional.ofNullable(dayTime), Optional.ofNullable(weather), Optional.ofNullable(y), Optional.ofNullable(temperature), operation, modifier);
        }
    }
}
