package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public record ClientboundStartSpinjitzuPayload(UUID uuid) implements ExtendedPacketPayload
{
    public static final Type<ClientboundStartSpinjitzuPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_start_spinjitzu"));
    public static final StreamCodec<ByteBuf, ClientboundStartSpinjitzuPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundStartSpinjitzuPayload::uuid,
            ClientboundStartSpinjitzuPayload::new
    );

    // On Client
    public void handle(Player player) {
        AbstractClientPlayer clientPlayer = ClientUtils.getClientPlayerByUUID(uuid);
        Services.DATA.setSpinjitzuData(new SpinjitzuData(true, true), clientPlayer);
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) AnimationUtils.startAnimation(PlayerAnimations.Spinjitzu.START.getAnimation(), PlayerAnimations.Spinjitzu.ACTIVE.getAnimation(), clientPlayer, FirstPersonMode.VANILLA);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
