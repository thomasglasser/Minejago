package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import dev.thomasglasser.tommylib.api.network.PacketUtils;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ClientboundStartSpinjitzuPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_start_spinjitzu");

    private final UUID uuid;

    public ClientboundStartSpinjitzuPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public static FriendlyByteBuf write(UUID uuid) {
        FriendlyByteBuf buf = PacketUtils.create();

        buf.writeUUID(uuid);

        return buf;
    }

    public void handle(@Nullable Player player) {
        AbstractClientPlayer clientPlayer = ClientUtils.getClientPlayerByUUID(uuid);
        Services.DATA.setSpinjitzuData(new SpinjitzuData(true, true), clientPlayer);
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) AnimationUtils.startAnimation(PlayerAnimations.Spinjitzu.START.getAnimation(), PlayerAnimations.Spinjitzu.ACTIVE.getAnimation(), clientPlayer, FirstPersonMode.VANILLA);
    }

    @Override
    public Direction direction()
    {
        return Direction.SERVER_TO_CLIENT;
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
