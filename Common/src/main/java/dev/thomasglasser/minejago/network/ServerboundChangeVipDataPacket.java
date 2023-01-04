package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterLayerOptions;
import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public record ServerboundChangeVipDataPacket(UUID uuid, boolean beta, BetaTesterLayerOptions choice, boolean dev) {
    public ServerboundChangeVipDataPacket(FriendlyByteBuf buf) {
        this(buf.readUUID(), buf.readBoolean(), buf.readEnum(BetaTesterLayerOptions.class), buf.readBoolean());
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUUID(uuid);
        buffer.writeBoolean(beta);
        buffer.writeEnum(choice);
        buffer.writeBoolean(dev);
    }

    public void handle(ServerPlayer serverPlayer) {
        Services.NETWORK.sendToAllClients(new ClientboundChangeVipDataPacket(uuid, beta, choice, dev), serverPlayer);
    }
}
