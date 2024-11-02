package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundStopSpinjitzuPayload(UUID uuid, boolean fail) implements ExtendedPacketPayload {
    public static final Type<ClientboundStopSpinjitzuPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_stop_spinjitzu"));
    public static final StreamCodec<ByteBuf, ClientboundStopSpinjitzuPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundStopSpinjitzuPayload::uuid,
            ByteBufCodecs.BOOL, ClientboundStopSpinjitzuPayload::fail,
            ClientboundStopSpinjitzuPayload::new);

    // On Client
    public void handle(Player player) {
        Player clientPlayer = ClientUtils.getPlayerByUUID(uuid);
        // TODO: Update playerAnimator
//        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) {
//            if (fail)
//                AnimationUtils.startAnimation(PlayerAnimations.Spinjitzu.WOBBLE.getAnimation(), null, clientPlayer, FirstPersonMode.VANILLA);
//            else
//                AnimationUtils.startAnimation(PlayerAnimations.Spinjitzu.FINISH.getAnimation(), null, clientPlayer, FirstPersonMode.VANILLA);
//        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
