package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.platform.services.INetworkHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class ForgeNetworkHelper implements INetworkHelper {
    @Override
    public <MSG> void sendToServer(Class<MSG> msgClass, FriendlyByteBuf args) {
        try {
            MinejagoMainChannel.sendToServer(msgClass.getDeclaredConstructor(FriendlyByteBuf.class).newInstance(args));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG> void sendToServer(Class<MSG> msgClass) {
        try {
            MinejagoMainChannel.sendToServer(msgClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG> void sendToClient(Class<MSG> msgClass, FriendlyByteBuf args, ServerPlayer player) {
        try {
            MinejagoMainChannel.sendToClient(msgClass.getDeclaredConstructor(FriendlyByteBuf.class).newInstance(args), player);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG> void sendToClient(Class<MSG> msgClass, ServerPlayer player) {
        try {
            MinejagoMainChannel.sendToClient(msgClass.getDeclaredConstructor().newInstance(), player);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG> void sendToAllClients(Class<MSG> msgClass, FriendlyByteBuf args, MinecraftServer server) {
        try {
            MinejagoMainChannel.sendToAllClients(msgClass.getDeclaredConstructor(FriendlyByteBuf.class).newInstance(args));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG> void sendToAllClients(Class<MSG> msgClass, MinecraftServer server) {
        try {
            MinejagoMainChannel.sendToAllClients(msgClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
