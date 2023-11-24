package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundStopMeditationPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_stop_meditation");

    // ON SERVER
    public void handle(ServerPlayer serverPlayer)
    {
        FocusData data = ((FocusDataHolder)serverPlayer).getFocusData();
        data.setMeditating(false);
        Services.NETWORK.sendToAllClients(ClientboundStopAnimationPacket.class, ClientboundStopAnimationPacket.toBytes(serverPlayer.getUUID()), serverPlayer.getServer());
    }
}
