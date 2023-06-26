package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundStopSpinjitzuPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_stop_spinjitzu");

    // ON SERVER
    public void handle(ServerPlayer serverPlayer)
    {
        SpinjitzuData spinjitzu = Services.DATA.getSpinjitzuData(serverPlayer);
        MinejagoEntityEvents.stopSpinjitzu(spinjitzu, serverPlayer, false);
    }
}
