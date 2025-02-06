package dev.thomasglasser.minejago.world.focus.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.thomasglasser.minejago.core.registries.MinejagoBuiltInRegistries;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFixedCodec;

public interface FocusModifier {
    Codec<FocusModifier> DIRECT_CODEC = MinejagoBuiltInRegistries.FOCUS_MODIFIER_SERIALIZER.byNameCodec()
            .dispatch(FocusModifier::codec, Function.identity());
    Codec<Holder<FocusModifier>> CODEC = RegistryFixedCodec.create(MinejagoRegistries.FOCUS_MODIFIER);

    Operation operation();

    double modifier();

    default double apply(double oldValue) {
        return operation().calculate(oldValue, modifier());
    }

    MapCodec<? extends FocusModifier> codec();

    static double checkAndApply(List<FocusModifier> modifiers, double oldValue) {
        if (!modifiers.isEmpty()) {
            double newValue = oldValue;
            for (FocusModifier modifier : modifiers) {
                newValue = modifier.apply(newValue);
            }
            return newValue;
        }
        return oldValue;
    }
}
