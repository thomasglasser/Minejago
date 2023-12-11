package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundStopMeditationPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_stop_meditation");

    private final boolean fail;

    public ServerboundStopMeditationPacket(FriendlyByteBuf buf) {
        fail = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(fail);
    }

    public static FriendlyByteBuf toBytes(boolean fail) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeBoolean(fail);

        return buf;
    }

    public void handle(ServerPlayer serverPlayer) {
        ((FocusDataHolder)serverPlayer).getFocusData().setMeditating(false);
        Services.NETWORK.sendToAllClients(ClientboundStopMeditationPacket.class, ClientboundStopMeditationPacket.toBytes(serverPlayer.getUUID(), fail), serverPlayer.getServer());
    }
}
