package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ServerboundStopMeditationPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_stop_meditation");

    private final boolean fail;

    public ServerboundStopMeditationPacket(FriendlyByteBuf buf) {
        fail = buf.readBoolean();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(fail);
    }

    public static FriendlyByteBuf write(boolean fail) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeBoolean(fail);

        return buf;
    }

    public void handle(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer)
        {
            Services.DATA.getFocusData(player).stopMeditating();
            Services.NETWORK.sendToAllClients(ClientboundStopMeditationPacket.class, ClientboundStopMeditationPacket.write(serverPlayer.getUUID(), fail), serverPlayer.getServer());
        }
    }

    @Override
    public Direction direction()
    {
        return Direction.CLIENT_TO_SERVER;
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
