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

public class ClientboundStopSpinjitzuPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_stop_spinjitzu");

    private final UUID uuid;
    private final boolean fail;

    public ClientboundStopSpinjitzuPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        fail = buf.readBoolean();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeBoolean(fail);
    }

    public static FriendlyByteBuf write(UUID uuid, boolean fail) {
        FriendlyByteBuf buf = PacketUtils.create();

        buf.writeUUID(uuid);
        buf.writeBoolean(fail);

        return buf;
    }

    public void handle(@Nullable Player player) {
        AbstractClientPlayer clientPlayer = ClientUtils.getClientPlayerByUUID(uuid);
        Services.DATA.setSpinjitzuData(new SpinjitzuData(Services.DATA.getSpinjitzuData(clientPlayer).unlocked(), false), clientPlayer);
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
        {
            if (fail)
                AnimationUtils.startAnimation(PlayerAnimations.Spinjitzu.WOBBLE.getAnimation(), null, clientPlayer, FirstPersonMode.VANILLA);
            else
                AnimationUtils.startAnimation(PlayerAnimations.Spinjitzu.FINISH.getAnimation(), null, clientPlayer, FirstPersonMode.VANILLA);
        }
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
