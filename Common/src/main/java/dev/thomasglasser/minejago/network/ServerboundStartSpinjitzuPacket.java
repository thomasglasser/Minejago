package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.server.level.ServerPlayer;

public record ServerboundStartSpinjitzuPacket() {
    // ON SERVER
    public void handle(ServerPlayer serverPlayer) {
        Services.DATA.setSpinjitzuData(new SpinjitzuData(true, true), serverPlayer);
        Services.NETWORK.sendToAllClients(new ClientboundStartSpinjitzuPacket(serverPlayer.getUUID()), serverPlayer);
    }
}
