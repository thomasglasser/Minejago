package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ClientboundStartMeditationPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_start_meditation");

    private final UUID uuid;

    public ClientboundStartMeditationPacket(FriendlyByteBuf buf) {
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
        ((FocusDataHolder)player).getFocusData().setMeditating(true);

        // TODO: Meditation animations
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
            MinejagoClientUtils.startAnimation(PlayerAnimations.Spinjitzu.START.getAnimation(), PlayerAnimations.Spinjitzu.ACTIVE.getAnimation(), player, FirstPersonMode.VANILLA);
    }
}
