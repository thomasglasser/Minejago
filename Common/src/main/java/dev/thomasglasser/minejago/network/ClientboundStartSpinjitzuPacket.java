package dev.thomasglasser.minejago.network;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.MinejagoAnimationUtils;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
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
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeUUID(uuid);

        return buf;
    }

    public void handle(@Nullable Player player) {
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) MinejagoAnimationUtils.startAnimation(PlayerAnimations.Spinjitzu.START.getAnimation(), PlayerAnimations.Spinjitzu.ACTIVE.getAnimation(), MinejagoClientUtils.getClientPlayerByUUID(uuid), FirstPersonMode.VANILLA);
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
