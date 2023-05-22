package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.INetworkHelper;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class FabricNetworkHelper implements INetworkHelper {
    @Override
    public <MSG> void sendToServer(Class<MSG> msgClass, FriendlyByteBuf args) {
        try {
            ClientPlayNetworking.send(((ResourceLocation) msgClass.getDeclaredField("ID").get(this)), args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG> void sendToServer(Class<MSG> msgClass) {
        try {
            ClientPlayNetworking.send(((ResourceLocation) msgClass.getDeclaredField("ID").get(this)), MinejagoPacketUtils.empty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG> void sendToClient(Class<MSG> msgClass, FriendlyByteBuf args, ServerPlayer player) {
        try {
            ServerPlayNetworking.send(player, ((ResourceLocation) msgClass.getDeclaredField("ID").get(this)), args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG> void sendToClient(Class<MSG> msgClass, ServerPlayer player) {
        try {
            ServerPlayNetworking.send(player, ((ResourceLocation) msgClass.getDeclaredField("ID").get(this)), MinejagoPacketUtils.empty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <MSG> void sendToAllClients(Class<MSG> msgClass, FriendlyByteBuf args, MinecraftServer server) {
        for (ServerPlayer player : PlayerLookup.all(server))
        {
            try {
                ServerPlayNetworking.send(player, ((ResourceLocation) msgClass.getDeclaredField("ID").get(this)), args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <MSG> void sendToAllClients(Class<MSG> msgClass, MinecraftServer server) {
        for (ServerPlayer player : PlayerLookup.all(server))
        {
            try {
                ServerPlayNetworking.send(player, ((ResourceLocation) msgClass.getDeclaredField("ID").get(this)), MinejagoPacketUtils.empty());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
