package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public record ClientboundStartMegaMeditationPayload(UUID uuid) implements ExtendedPacketPayload
{
    public static final Type<ClientboundStartMegaMeditationPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_start_mega_meditation"));
    public static final StreamCodec<ByteBuf, ClientboundStartMegaMeditationPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundStartMegaMeditationPayload::uuid,
            ClientboundStartMegaMeditationPayload::new
    );

    // On Client
    public void handle(Player player)
    {
        player = ClientUtils.getClientPlayerByUUID(uuid);
        Services.DATA.getFocusData(player).startMegaMeditating();

        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
            AnimationUtils.startAnimation(PlayerAnimations.Meditation.FLOAT.getAnimation(), player, FirstPersonMode.VANILLA);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
