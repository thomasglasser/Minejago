package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public record ClientboundStopAnimationPayload(UUID uuid) implements ExtendedPacketPayload {
    public static final Type<ClientboundStopAnimationPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_stop_animation"));
    public static final StreamCodec<ByteBuf, ClientboundStopAnimationPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundStopAnimationPayload::uuid,
            ClientboundStopAnimationPayload::new);

    // ON CLIENT
    public void handle(@Nullable Player player) {
        AnimationUtils.stopAnimation(ClientUtils.getPlayerByUUID(uuid));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
