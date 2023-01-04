package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterLayerOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public record ClientboundChangeVipDataPacket(UUID uuid, boolean beta, BetaTesterLayerOptions choice, boolean dev) {
    public ClientboundChangeVipDataPacket(FriendlyByteBuf buf) {
        this(buf.readUUID(), buf.readBoolean(), buf.readEnum(BetaTesterLayerOptions.class), buf.readBoolean());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeBoolean(beta);
        buf.writeEnum(choice);
        buf.writeBoolean(dev);
    }

    // ON CLIENT
    public void handle()
    {
        MinejagoClientUtils.setVipData(MinejagoClientUtils.getClientPlayerByUUID(uuid), new VipData(choice, beta, dev));
    }
}
