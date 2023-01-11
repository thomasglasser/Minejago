package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundStartSpinjitzuPacket {
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_start_spinjitzu");

    // ON SERVER
    public void handle(ServerPlayer serverPlayer) {
        Services.DATA.setSpinjitzuData(new SpinjitzuData(true, true), serverPlayer);
        Services.NETWORK.sendToAllClients(ClientboundStartSpinjitzuPacket.class, ClientboundStartSpinjitzuPacket.toBytes(serverPlayer.getUUID()), serverPlayer);
    }
}
