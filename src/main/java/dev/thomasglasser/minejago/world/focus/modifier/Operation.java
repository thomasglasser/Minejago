package dev.thomasglasser.minejago.world.focus.modifier;

import java.util.function.BiFunction;

public enum Operation
{
	ADDITION(Double::sum),
	SUBTRACTION((a, b) -> a - b),
	MULTIPLICATION((a, b) -> a * b),
	DIVISION((a, b) -> a / b);

	final BiFunction<Double, Double, Double> operation;

	Operation(BiFunction<Double, Double, Double> operation)
	{
		this.operation = operation;
	}

	public double calculate(double a, double b)
	{
		return operation.apply(a, b);
	}

	public static Operation of(String s)
	{
		return valueOf(s.toUpperCase());
	}
}
