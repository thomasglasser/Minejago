package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.PlayerAnimationHandler;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundStopMeditationPayload(UUID uuid, boolean fail) implements ExtendedPacketPayload {
    public static final Type<ClientboundStopMeditationPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_stop_meditation"));
    public static final StreamCodec<ByteBuf, ClientboundStopMeditationPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundStopMeditationPayload::uuid,
            ByteBufCodecs.BOOL, ClientboundStopMeditationPayload::fail,
            ClientboundStopMeditationPayload::new);

    // On Client
    public void handle(Player player) {
        Player clientPlayer = ClientUtils.getPlayerByUUID(uuid);
        FocusData focusData = clientPlayer.getData(MinejagoAttachmentTypes.FOCUS);
        focusData.stopMeditating();
        player.refreshDimensions();
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) {
            if (fail)
                PlayerAnimationHandler.stopAnimation(clientPlayer);
//            else
//                PlayerAnimationHandler.startAnimation(PlayerAnimations.Meditation.FINISH.getAnimation(), clientPlayer);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
