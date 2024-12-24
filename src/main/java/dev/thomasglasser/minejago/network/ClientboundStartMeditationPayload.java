package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundStartMeditationPayload(UUID uuid) implements ExtendedPacketPayload {
    public static final Type<ClientboundStartMeditationPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_start_meditation"));
    public static final StreamCodec<ByteBuf, ClientboundStartMeditationPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundStartMeditationPayload::uuid,
            ClientboundStartMeditationPayload::new);

    // On Client
    @Override
    public void handle(Player player) {
        player = ClientUtils.getPlayerByUUID(uuid);
        player.getData(MinejagoAttachmentTypes.FOCUS).startMeditating();
        player.refreshDimensions();

        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
            AnimationUtils.startAnimation(PlayerAnimations.Meditation.START.getAnimation(), player, FirstPersonMode.VANILLA);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
