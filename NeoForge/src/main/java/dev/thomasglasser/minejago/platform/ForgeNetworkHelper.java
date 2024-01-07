package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.network.CustomPacket;
import dev.thomasglasser.minejago.platform.services.NetworkHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class ForgeNetworkHelper implements NetworkHelper {
    @Override
    public <MSG extends CustomPacket> void sendToServer(Class<MSG> msgClass, FriendlyByteBuf args) {
        try {
            PacketDistributor.SERVER.noArg().send(msgClass.getDeclaredConstructor(FriendlyByteBuf.class).newInstance(args));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG extends CustomPacket> void sendToServer(Class<MSG> msgClass) {
        try {
            PacketDistributor.SERVER.noArg().send(msgClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG extends CustomPacket> void sendToClient(Class<MSG> msgClass, FriendlyByteBuf args, ServerPlayer player) {
        try {
            PacketDistributor.PLAYER.with(player).send(msgClass.getDeclaredConstructor(FriendlyByteBuf.class).newInstance(args));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG extends CustomPacket> void sendToClient(Class<MSG> msgClass, ServerPlayer player) {
        try {
            PacketDistributor.PLAYER.with(player).send(msgClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG extends CustomPacket> void sendToAllClients(Class<MSG> msgClass, FriendlyByteBuf args, MinecraftServer server) {
        try {
            PacketDistributor.ALL.noArg().send(msgClass.getDeclaredConstructor(FriendlyByteBuf.class).newInstance(args));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG extends CustomPacket> void sendToAllClients(Class<MSG> msgClass, MinecraftServer server) {
        try {
            PacketDistributor.ALL.noArg().send(msgClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
