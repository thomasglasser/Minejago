package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.ItemAnimations;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.network.NetworkUtils;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public record ClientboundStartScytheAnimationPayload(UUID uuid, ItemAnimations.ScytheOfQuakes startAnim, Optional<ItemAnimations.ScytheOfQuakes> goAnim) implements ExtendedPacketPayload
{
    public static final Type<ClientboundStartScytheAnimationPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_start_scythe_animation"));
    public static final StreamCodec<FriendlyByteBuf, ClientboundStartScytheAnimationPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundStartScytheAnimationPayload::uuid,
            NetworkUtils.enumCodec(ItemAnimations.ScytheOfQuakes.class), ClientboundStartScytheAnimationPayload::startAnim,
            ByteBufCodecs.optional(NetworkUtils.enumCodec(ItemAnimations.ScytheOfQuakes.class)), ClientboundStartScytheAnimationPayload::goAnim,
            ClientboundStartScytheAnimationPayload::new
    );

    // ON CLIENT
    public void handle(@Nullable Player player) {
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) AnimationUtils.startAnimation(startAnim.getAnimation(), goAnim.map(ItemAnimations.ScytheOfQuakes::getAnimation).orElse(null), ClientUtils.getClientPlayerByUUID(uuid), FirstPersonMode.THIRD_PERSON_MODEL);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
