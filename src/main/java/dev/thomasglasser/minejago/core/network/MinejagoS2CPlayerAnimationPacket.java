package dev.thomasglasser.minejago.core.network;

import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.client.animation.definitions.SpinjitzuAnimation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MinejagoS2CPlayerAnimationPacket {

    private PlayerAnimations anim;

    public MinejagoS2CPlayerAnimationPacket(PlayerAnimations anim) {
        this.anim = anim;
    }

    public MinejagoS2CPlayerAnimationPacket(FriendlyByteBuf buf) {
        anim = buf.readEnum(PlayerAnimations.class);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(anim);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            switch (anim)
            {
                case SPINJITZU -> SpinjitzuAnimation.startAnimation();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
