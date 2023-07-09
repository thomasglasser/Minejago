package dev.thomasglasser.minejago.platform.services;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public interface NetworkHelper
{
    <MSG> void sendToServer(Class<MSG> msgClass, FriendlyByteBuf args);
    <MSG> void sendToServer(Class<MSG> msgClass);
    <MSG> void sendToClient(Class<MSG> msgClass, FriendlyByteBuf args, ServerPlayer player);
    <MSG> void sendToClient(Class<MSG> msgClass, ServerPlayer player);
    <MSG> void sendToAllClients(Class<MSG> msgClass, FriendlyByteBuf args, MinecraftServer server);
    <MSG> void sendToAllClients(Class<MSG> msgClass, MinecraftServer server);
}
