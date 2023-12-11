package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.DataHolder;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundStartMeditationPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_start_meditation");

    // ON SERVER
    public void handle(ServerPlayer serverPlayer) {
        ((FocusDataHolder)serverPlayer).getFocusData().setMeditating();
        ((DataHolder)serverPlayer).getPersistentData().putString("StartPos", serverPlayer.blockPosition().toString());
        Services.NETWORK.sendToAllClients(ClientboundStartMeditationPacket.class, ClientboundStartMeditationPacket.toBytes(serverPlayer.getUUID()), serverPlayer.getServer());
    }
}
