package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterLayerOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

public class ClientboundChangeVipDataPacket {

    private UUID uuid;
    private boolean beta;
    private BetaTesterLayerOptions choice;
    private boolean dev;

    public ClientboundChangeVipDataPacket(UUID uuid, boolean beta, @Nullable BetaTesterLayerOptions choice, boolean dev) {
        this.uuid = uuid;
        this.beta = beta;
        this.choice = choice;
        this.dev = dev;
    }

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

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            MinejagoClientUtils.setVipData(MinejagoClientUtils.getClientPlayerByUUID(uuid), new VipData(choice, beta, dev));
        });
        ctx.get().setPacketHandled(true);
    }
}
