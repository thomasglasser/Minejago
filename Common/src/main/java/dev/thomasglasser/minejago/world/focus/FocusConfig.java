package dev.thomasglasser.minejago.world.focus;

import net.minecraftforge.common.ForgeConfigSpec;

public class FocusConfig
{
	public static ForgeConfigSpec.IntValue X_OFFSET;
	public static ForgeConfigSpec.IntValue Y_OFFSET;

	public static void registerClient(ForgeConfigSpec.Builder builder)
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
