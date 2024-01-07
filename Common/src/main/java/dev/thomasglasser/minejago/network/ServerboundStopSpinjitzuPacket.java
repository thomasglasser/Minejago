package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ServerboundStopSpinjitzuPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_stop_spinjitzu");

    // ON SERVER
    public void handle(@Nullable Player player)
    {
        ServerPlayer serverPlayer = ((ServerPlayer) player);
        SpinjitzuData spinjitzu = Services.DATA.getSpinjitzuData(serverPlayer);
        MinejagoEntityEvents.stopSpinjitzu(spinjitzu, serverPlayer, false);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {}

    @Override
    public ResourceLocation id()
    {
        return ID;
    }

    @Override
    public Direction direction()
    {
        return Direction.CLIENT_TO_SERVER;
    }
}
