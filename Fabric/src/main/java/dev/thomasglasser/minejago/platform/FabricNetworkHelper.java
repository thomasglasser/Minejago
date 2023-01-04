package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.INetworkHelper;
import net.minecraft.server.level.ServerPlayer;

// TODO: Implement w networking API
public class FabricNetworkHelper implements INetworkHelper {
    @Override
    public <MSG> void sendToServer(MSG message) {

    }

    @Override
    public <MSG> void sendToClient(MSG msg, ServerPlayer player) {

    }

    @Override
    public <MSG> void sendToAllClients(MSG msg, ServerPlayer player) {

    }
}
