package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.SpinjitzuAnimations;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class ClientboundFailSpinjitzuPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_fail_spinjitzu");

    private final UUID uuid;

    public ClientboundFailSpinjitzuPacket(FriendlyByteBuf buf) {
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

    public void handle() {
        if (Services.PLATFORM.isModLoaded(Minejago.Dependencies.PLAYER_ANIMATOR.getModId())) MinejagoClientUtils.startAnimation(SpinjitzuAnimations.Animations.SPINJITZU_WOBBLE.getAnimation(), null, MinejagoClientUtils.getClientPlayerByUUID(uuid), FirstPersonMode.VANILLA);
    }

}
