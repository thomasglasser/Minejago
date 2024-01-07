package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.MinejagoAnimationUtils;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.focus.FocusData;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ClientboundStopMeditationPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_stop_meditation");

    private final UUID uuid;
    private final boolean fail;

    public ClientboundStopMeditationPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        fail = buf.readBoolean();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeBoolean(fail);
    }

    public static FriendlyByteBuf write(UUID uuid, boolean fail) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeUUID(uuid);
        buf.writeBoolean(fail);

        return buf;
    }

    public void handle(@Nullable Player player) {
        AbstractClientPlayer clientPlayer = MinejagoClientUtils.getClientPlayerByUUID(uuid);
        FocusData focusData = Services.DATA.getFocusData(clientPlayer);
        focusData.stopMeditating();
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
        {
            if (fail)
                MinejagoAnimationUtils.stopAnimation(clientPlayer);
            else
                MinejagoAnimationUtils.startAnimation(PlayerAnimations.Meditation.FINISH.getAnimation(), clientPlayer, FirstPersonMode.VANILLA);
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