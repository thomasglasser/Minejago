package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public record ClientboundStopMeditationPayload(UUID uuid, boolean fail) implements ExtendedPacketPayload
{
    public static final Type<ClientboundStopMeditationPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_stop_meditation"));
    public static final StreamCodec<ByteBuf, ClientboundStopMeditationPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundStopMeditationPayload::uuid,
            ByteBufCodecs.BOOL, ClientboundStopMeditationPayload::fail,
            ClientboundStopMeditationPayload::new
    );

    // On Client
    public void handle(Player player) {
        AbstractClientPlayer clientPlayer = ClientUtils.getClientPlayerByUUID(uuid);
        FocusData focusData = Services.DATA.getFocusData(clientPlayer);
        focusData.stopMeditating();
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
        {
            if (fail)
                AnimationUtils.stopAnimation(clientPlayer);
            else
                AnimationUtils.startAnimation(PlayerAnimations.Meditation.FINISH.getAnimation(), clientPlayer, FirstPersonMode.VANILLA);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}