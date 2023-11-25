package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterLayerOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class ClientboundChangeVipDataPacket {
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_change_vip_data");

    private final UUID uuid;
    private final VipData vipData;

    public ClientboundChangeVipDataPacket(FriendlyByteBuf buf) {
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
        return ServerboundChangeVipDataPacket.toBytes(uuid, vipData);
    }

    // ON CLIENT
    public void handle()
    {
        MinejagoClientUtils.setVipData(MinejagoClientUtils.getClientPlayerByUUID(uuid), vipData);
    }
}
