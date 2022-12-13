package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.world.level.storage.ActivatedSpinjitzuCapabilityAttacher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundStartSpinjitzuPacket {

    public ServerboundStartSpinjitzuPacket() {
    }

    public ServerboundStartSpinjitzuPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ctx.get().getSender().getCapability(ActivatedSpinjitzuCapabilityAttacher.ACTIVATED_SPINJITZU_CAPABILITY).ifPresent(cap -> cap.setActive(true));
            MinejagoMainChannel.sendToAllClients(new ClientboundStartSpinjitzuPacket(ctx.get().getSender().getUUID()), ctx.get().getSender());
        });
        ctx.get().setPacketHandled(true);
    }
}
