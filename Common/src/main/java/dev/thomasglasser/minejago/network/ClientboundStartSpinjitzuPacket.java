package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.client.animation.definitions.SpinjitzuAnimations;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public record ClientboundStartSpinjitzuPacket(UUID uuid) {
    public ClientboundStartSpinjitzuPacket(FriendlyByteBuf buf) {
        this(buf.readUUID());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public void handle() {
        MinejagoClientUtils.startAnimation(SpinjitzuAnimations.Animations.SPINJITZU_START.getAnimation(), SpinjitzuAnimations.Animations.SPINJITZU_ACTIVE.getAnimation(), MinejagoClientUtils.getClientPlayerByUUID(uuid));
    }
}
