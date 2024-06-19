package dev.thomasglasser.minejago.plugins;

import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import net.minecraft.world.entity.EntityType;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandler;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandlers;

public class MinejagoDynamicLights
{
	public static void register()
	{
		DynamicLightHandlers.registerDynamicLightHandler(EntityType.PLAYER, DynamicLightHandler.makeHandler(player -> player.getData(MinejagoAttachmentTypes.SPINJITZU).active() ? 7 : 0, player -> false));
		DynamicLightHandlers.registerDynamicLightHandler(MinejagoEntityTypes.WU.get(), DynamicLightHandler.makeHandler(wu -> wu.getData(MinejagoAttachmentTypes.SPINJITZU).active() ? 7 : 0, wu -> false));
	}
}
