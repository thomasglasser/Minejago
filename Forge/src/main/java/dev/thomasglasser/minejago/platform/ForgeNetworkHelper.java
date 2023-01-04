package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.platform.services.INetworkHelper;
import net.minecraft.server.level.ServerPlayer;

public class ForgeNetworkHelper implements INetworkHelper {
    @Override
    public <MSG> void sendToServer(MSG message) {
        MinejagoMainChannel.sendToServer(message);
    }

    @Override
    public <MSG> void sendToClient(MSG msg, ServerPlayer player) {
        MinejagoMainChannel.sendToClient(msg, player);
    }

    @Override
    public <MSG> void sendToAllClients(MSG msg, ServerPlayer player) {
        MinejagoMainChannel.sendToAllClients(msg, player);
    }
}
