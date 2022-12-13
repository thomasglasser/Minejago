package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.client.animation.definitions.ItemAnimations;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

public class ClientboundStartScytheAnimationPacket {

    private UUID uuid;
    private ItemAnimations.Animations startAnim;
    private ItemAnimations.Animations goAnim;

    public ClientboundStartScytheAnimationPacket(UUID uuid, ItemAnimations.Animations startAnim, ItemAnimations.Animations goAnim)
    {
        this.uuid = uuid;
        this.startAnim = startAnim;
        this.goAnim = goAnim;
    }

    public ClientboundStartScytheAnimationPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        startAnim = buf.readEnum(ItemAnimations.Animations.class);
        goAnim = buf.readEnum(ItemAnimations.Animations.class);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeEnum(startAnim);
        buf.writeEnum(goAnim);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            MinejagoClientUtils.startAnimation(startAnim.getAnimation(), goAnim != null ? goAnim.getAnimation() : null, MinejagoClientUtils.getClientPlayerByUUID(uuid));
        });
        ctx.get().setPacketHandled(true);
    }
}
