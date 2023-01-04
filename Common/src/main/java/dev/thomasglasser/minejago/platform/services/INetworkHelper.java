package dev.thomasglasser.minejago.platform.services;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHelper
{
    <MSG> void sendToServer(MSG message);
    <MSG> void sendToClient(MSG msg, ServerPlayer player);
    <MSG> void sendToAllClients(MSG msg, ServerPlayer player);
}
