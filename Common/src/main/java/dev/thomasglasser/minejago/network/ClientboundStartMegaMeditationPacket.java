package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.MinejagoAnimationUtils;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ClientboundStartMegaMeditationPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_start_mega_meditation");

    private final UUID uuid;

    public ClientboundStartMegaMeditationPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public static FriendlyByteBuf toBytes(UUID uuid) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeUUID(uuid);

        return buf;
    }

    public void handle()
    {
        Player player = MinejagoClientUtils.getClientPlayerByUUID(uuid);
        ((FocusDataHolder)player).getFocusData().startMegaMeditating();

        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
            MinejagoAnimationUtils.startAnimation(PlayerAnimations.Meditation.FLOAT.getAnimation(), player, FirstPersonMode.VANILLA);
//            MinejagoClientUtils.startAnimation(PlayerAnimations.Meditation.RISE.getAnimation(), PlayerAnimations.Meditation.FLOAT.getAnimation(), player, FirstPersonMode.VANILLA);
    }
}
