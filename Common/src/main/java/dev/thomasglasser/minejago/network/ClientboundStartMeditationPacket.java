package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import dev.thomasglasser.tommylib.api.network.PacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ClientboundStartMeditationPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_start_meditation");

    private final UUID uuid;

    public ClientboundStartMeditationPacket(FriendlyByteBuf buf) {
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

    public void handle(@Nullable Player player)
    {
        player = ClientUtils.getClientPlayerByUUID(uuid);
        Services.DATA.getFocusData(player).startMeditating();

        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
            AnimationUtils.startAnimation(PlayerAnimations.Meditation.START.getAnimation(), player, FirstPersonMode.VANILLA);
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
