package dev.thomasglasser.minejago.client;

import org.thinkingstudio.ryoamiclights.fabric.api.DynamicLightsInitializer;

public class MinejagoDynamicLightsInitializer implements DynamicLightsInitializer
{
	@Override
	public void onInitializeDynamicLights()
	{
		MinejagoDynamicLights.register();
	}
}
