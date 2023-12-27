package dev.thomasglasser.minejago.world.focus.modifier.world;

public enum Weather
{
	CLEAR,
	RAIN,
	THUNDER_RAIN,
	SNOW,
	THUNDER_SNOW;

	public static Weather of(String s)
	{
		return valueOf(s.toUpperCase());
	}
}
