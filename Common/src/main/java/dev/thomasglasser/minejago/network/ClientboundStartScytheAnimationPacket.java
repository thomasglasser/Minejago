package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.client.animation.definitions.ItemAnimations;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public record ClientboundStartScytheAnimationPacket(UUID uuid, ItemAnimations.Animations startAnim, ItemAnimations.Animations goAnim) {
    public ClientboundStartScytheAnimationPacket(UUID uuid, ItemAnimations.Animations startAnim, ItemAnimations.Animations goAnim)
    {
        this.uuid = uuid;
        this.startAnim = startAnim;
        this.goAnim = goAnim;
    }

    public ClientboundStartScytheAnimationPacket(FriendlyByteBuf buf) {
        this(buf.readUUID(), buf.readEnum(ItemAnimations.Animations.class), buf.readEnum(ItemAnimations.Animations.class));
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeEnum(startAnim);
        buf.writeEnum(goAnim);
    }

    // ON CLIENT
    public void handle() {
        MinejagoClientUtils.startAnimation(startAnim.getAnimation(), goAnim != null ? goAnim.getAnimation() : null, MinejagoClientUtils.getClientPlayerByUUID(uuid));
    }
}
