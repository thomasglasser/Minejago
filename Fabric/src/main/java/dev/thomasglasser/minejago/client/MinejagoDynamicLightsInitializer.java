package dev.thomasglasser.minejago.client;

import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;

public class MinejagoDynamicLightsInitializer implements DynamicLightsInitializer
{
	@Override
	public void onInitializeDynamicLights()
	{
		MinejagoClientEvents.registerDynamicLights();
	}
}
