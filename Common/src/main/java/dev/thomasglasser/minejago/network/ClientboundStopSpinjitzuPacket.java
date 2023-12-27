package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.MinejagoAnimationUtils;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class ClientboundStopSpinjitzuPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_stop_spinjitzu");

    private final UUID uuid;
    private final boolean fail;

    public ClientboundStopSpinjitzuPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        fail = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeBoolean(fail);
    }

    public static FriendlyByteBuf toBytes(UUID uuid, boolean fail) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeUUID(uuid);
        buf.writeBoolean(fail);

        return buf;
    }

    public void handle() {
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
        {
            if (fail)
                MinejagoAnimationUtils.startAnimation(PlayerAnimations.Spinjitzu.WOBBLE.getAnimation(), null, MinejagoClientUtils.getClientPlayerByUUID(uuid), FirstPersonMode.VANILLA);
            else
                MinejagoAnimationUtils.startAnimation(PlayerAnimations.Spinjitzu.FINISH.getAnimation(), null, MinejagoClientUtils.getClientPlayerByUUID(uuid), FirstPersonMode.VANILLA);
        }
    }
}
