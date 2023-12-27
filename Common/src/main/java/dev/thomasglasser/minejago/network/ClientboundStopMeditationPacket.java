package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.MinejagoAnimationUtils;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class ClientboundStopMeditationPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_stop_meditation");

    private final UUID uuid;
    private final boolean fail;

    public ClientboundStopMeditationPacket(FriendlyByteBuf buf) {
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
        FocusData focusData = ((FocusDataHolder) MinejagoClientUtils.getClientPlayerByUUID(uuid)).getFocusData();
        focusData.stopMeditating();
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled())
        {
            if (fail)
                MinejagoAnimationUtils.stopAnimation(MinejagoClientUtils.getClientPlayerByUUID(uuid));
            else
                MinejagoAnimationUtils.startAnimation(/*focusData.canDoMegaFocus() ? PlayerAnimations.Meditation.FALL.getAnimation() : */PlayerAnimations.Meditation.FINISH.getAnimation(), MinejagoClientUtils.getClientPlayerByUUID(uuid), FirstPersonMode.VANILLA);
        }
    }
}