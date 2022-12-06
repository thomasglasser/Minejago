package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.client.animation.definitions.SpinjitzuAnimation;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ClientboundActivateSpinjitzuPacket {

    private UUID uuid;

    public ClientboundActivateSpinjitzuPacket(UUID uuid)
    {
        this.uuid = uuid;
    }

    public ClientboundActivateSpinjitzuPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            SpinjitzuAnimation.startAnimation(MinejagoClientUtils.getClientPlayerByUUID(uuid));
        });
        ctx.get().setPacketHandled(true);
    }
}
