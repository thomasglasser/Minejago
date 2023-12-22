package dev.thomasglasser.minejago.world.focus;

import net.neoforged.neoforge.common.ModConfigSpec;

public class FocusConfig
{
	public static ModConfigSpec.IntValue X_OFFSET;
	public static ModConfigSpec.IntValue Y_OFFSET;

	public static void registerClient(ModConfigSpec.Builder builder)
	{
		builder.comment("Settings for focus bar").push("focus");

		X_OFFSET = builder
				.comment("Horizontal pixels off from the normal position")
				.defineInRange("x", 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);

		Y_OFFSET = builder
				.comment("Vertical pixels off from the normal position")
				.defineInRange("y", 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);

		builder.pop();
	}
}
