package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ClientboundStopAnimationPacket {

    private UUID uuid;

    public ClientboundStopAnimationPacket(UUID uuid)
    {
        this.uuid = uuid;
    }

    public ClientboundStopAnimationPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            MinejagoClientUtils.stopAnimation(MinejagoClientUtils.getClientPlayerByUUID(uuid));
        });
        ctx.get().setPacketHandled(true);
    }
}
