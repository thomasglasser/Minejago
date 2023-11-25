package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterLayerOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class ServerboundChangeVipDataPacket {
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_change_vip_data");

    private final UUID uuid;
    private final VipData vipData;

    public ServerboundChangeVipDataPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        vipData = new VipData(
                buf.readEnum(SnapshotTesterLayerOptions.class),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean()
        );
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUUID(uuid);
        buffer.writeEnum(vipData.choice());
        buffer.writeBoolean(vipData.displaySnapshot());
        buffer.writeBoolean(vipData.displayDev());
        buffer.writeBoolean(vipData.displayOgDev());
    }

    public static FriendlyByteBuf toBytes(UUID uuid, VipData vipData) {
        FriendlyByteBuf buffer = MinejagoPacketUtils.create();

        buffer.writeUUID(uuid);
        buffer.writeEnum(vipData.choice());
        buffer.writeBoolean(vipData.displaySnapshot());
        buffer.writeBoolean(vipData.displayDev());
        buffer.writeBoolean(vipData.displayOgDev());

        return buffer;
    }

    public void handle(ServerPlayer serverPlayer) {
        Services.NETWORK.sendToAllClients(ClientboundChangeVipDataPacket.class, ClientboundChangeVipDataPacket.toBytes(uuid, vipData), serverPlayer.getServer());
    }
}
