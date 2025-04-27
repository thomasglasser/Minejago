package dev.thomasglasser.minejago.world.focus.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public record LocationFocusModifier(LocationPredicate location, Operation operation, double modifier) implements FocusModifier {

    public static final MapCodec<LocationFocusModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LocationPredicate.CODEC.fieldOf("location").forGetter(LocationFocusModifier::location),
            Operation.CODEC.fieldOf("operation").forGetter(LocationFocusModifier::operation),
            Codec.DOUBLE.fieldOf("modifier").forGetter(LocationFocusModifier::modifier)).apply(instance, LocationFocusModifier::new));
    @Override
    public MapCodec<? extends FocusModifier> codec() {
        return FocusModifierSerializers.LOCATION_FOCUS_MODIFIER.get();
    }

    public static double checkAndApply(ServerLevel level, Vec3 pos, double oldValue) {
        return FocusModifier.checkAndApply(level.holderLookup(MinejagoRegistries.FOCUS_MODIFIER).listElements().map(Holder.Reference::value).filter(modifier -> modifier instanceof LocationFocusModifier locationFocusModifier && locationFocusModifier.location().matches(level, pos.x(), pos.y(), pos.z())).toList(), oldValue);
    }
}
