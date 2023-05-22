package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterLayerOptions;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class ServerboundChangeVipDataPacket {
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_change_vip_data");

    private final UUID uuid;
    private final boolean beta;
    private final BetaTesterLayerOptions choice;
    private final boolean dev;

    public ServerboundChangeVipDataPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        beta = buf.readBoolean();
        choice = buf.readEnum(BetaTesterLayerOptions.class);
        dev = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUUID(uuid);
        buffer.writeBoolean(beta);
        buffer.writeEnum(choice);
        buffer.writeBoolean(dev);
    }

    public static FriendlyByteBuf toBytes(UUID uuid, boolean beta, BetaTesterLayerOptions choice, boolean dev) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeUUID(uuid);
        buf.writeBoolean(beta);
        buf.writeEnum(choice);
        buf.writeBoolean(dev);

        return buf;
    }

    public void handle(ServerPlayer serverPlayer) {
        Services.NETWORK.sendToAllClients(ClientboundChangeVipDataPacket.class, ClientboundChangeVipDataPacket.toBytes(uuid, beta, choice, dev), serverPlayer.getServer());
    }
}
