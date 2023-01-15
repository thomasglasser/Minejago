package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterLayerOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class ClientboundChangeVipDataPacket {
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_change_vip_data");

    private final UUID uuid;
    private final boolean beta;
    private final BetaTesterLayerOptions choice;
    private final boolean dev;

    public ClientboundChangeVipDataPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        beta = buf.readBoolean();
        choice = buf.readEnum(BetaTesterLayerOptions.class);
        dev = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeBoolean(beta);
        buf.writeEnum(choice);
        buf.writeBoolean(dev);
    }

    public static FriendlyByteBuf toBytes(UUID uuid, boolean beta, BetaTesterLayerOptions choice, boolean dev) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeUUID(uuid);
        buf.writeBoolean(beta);
        buf.writeEnum(choice);
        buf.writeBoolean(dev);

        return buf;
    }

    // ON CLIENT
    public void handle()
    {
        MinejagoClientUtils.setVipData(MinejagoClientUtils.getClientPlayerByUUID(uuid), new VipData(choice, beta, dev));
    }
}
