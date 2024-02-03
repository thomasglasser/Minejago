package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import dev.thomasglasser.tommylib.api.network.PacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ClientboundStopAnimationPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_stop_animation");

    private final UUID uuid;

    public ClientboundStopAnimationPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public static FriendlyByteBuf write(UUID uuid)
    {
        FriendlyByteBuf buf = PacketUtils.create();

        buf.writeUUID(uuid);

        return buf;
    }

    // ON CLIENT
    public void handle(@Nullable Player player) {
        AnimationUtils.stopAnimation(ClientUtils.getClientPlayerByUUID(uuid));
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
