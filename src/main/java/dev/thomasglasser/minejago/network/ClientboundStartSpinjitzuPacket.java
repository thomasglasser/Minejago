package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.client.animation.definitions.SpinjitzuAnimations;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ClientboundStartSpinjitzuPacket {

    private UUID uuid;

    public ClientboundStartSpinjitzuPacket(UUID uuid)
    {
        this.uuid = uuid;
    }

    public ClientboundStartSpinjitzuPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            MinejagoClientUtils.startAnimation(SpinjitzuAnimations.Animations.SPINJITZU_START.getAnimation(), SpinjitzuAnimations.Animations.SPINJITZU_ACTIVE.getAnimation(), MinejagoClientUtils.getClientPlayerByUUID(uuid));
        });
        ctx.get().setPacketHandled(true);
    }
}
