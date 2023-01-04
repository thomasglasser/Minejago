package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public record ClientboundStopAnimationPacket(UUID uuid) {
    public ClientboundStopAnimationPacket(FriendlyByteBuf buf) {
        this(buf.readUUID());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    // ON CLIENT
    public void handle() {
        MinejagoClientUtils.stopAnimation(MinejagoClientUtils.getClientPlayerByUUID(uuid));
    }
}
