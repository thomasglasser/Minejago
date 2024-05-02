package dev.thomasglasser.minejago.plugins;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import net.minecraft.world.entity.EntityType;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandler;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandlers;

public class MinejagoDynamicLights
{
	public static void register()
	{
		DynamicLightHandlers.registerDynamicLightHandler(EntityType.PLAYER, DynamicLightHandler.makeHandler(player -> Services.DATA.getSpinjitzuData(player).active() ? 7 : 0, player -> false));
		DynamicLightHandlers.registerDynamicLightHandler(MinejagoEntityTypes.WU.get(), DynamicLightHandler.makeHandler(wu -> Services.DATA.getSpinjitzuData(wu).active() ? 7 : 0, wu -> false));
	}
}
