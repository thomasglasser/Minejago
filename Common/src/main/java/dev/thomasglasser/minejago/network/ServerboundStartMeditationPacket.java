package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.DataHolder;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ServerboundStartMeditationPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_start_meditation");

    // ON SERVER
    public void handle(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer)
        {
            ((FocusDataHolder) serverPlayer).getFocusData().startMeditating();
            ((DataHolder) serverPlayer).getPersistentData().putString("StartPos", serverPlayer.blockPosition().toString());
            Services.NETWORK.sendToAllClients(ClientboundStartMeditationPacket.class, ClientboundStartMeditationPacket.write(serverPlayer.getUUID()), serverPlayer.getServer());
        }
    }

    @Override
    public Direction direction()
    {
        return Direction.CLIENT_TO_SERVER;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {}

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
