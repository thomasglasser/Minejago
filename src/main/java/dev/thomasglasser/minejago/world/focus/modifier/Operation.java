package dev.thomasglasser.minejago.world.focus.modifier;

import com.mojang.serialization.Codec;
import java.util.function.BiFunction;
import net.minecraft.util.StringRepresentable;

public enum Operation implements StringRepresentable {
    ADDITION(Double::sum),
    SUBTRACTION((a, b) -> a - b),
    MULTIPLICATION((a, b) -> a * b),
    DIVISION((a, b) -> a / b);

    public static final Codec<Operation> CODEC = Codec.STRING.xmap(Operation::of, Operation::getSerializedName);

    final BiFunction<Double, Double, Double> operation;

    Operation(BiFunction<Double, Double, Double> operation) {
        this.operation = operation;
    }

    public double calculate(double a, double b) {
        return operation.apply(a, b);
    }

    public static Operation of(String s) {
        return valueOf(s.toUpperCase());
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }
}
