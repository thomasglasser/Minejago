package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public record ServerboundStopSpinjitzuPayload() implements ExtendedPacketPayload
{
    public static final ServerboundStopSpinjitzuPayload INSTANCE = new ServerboundStopSpinjitzuPayload();

    public static final Type<ServerboundStopSpinjitzuPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_stop_spinjitzu"));
    public static final StreamCodec<ByteBuf, ServerboundStopSpinjitzuPayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON SERVER
    public void handle(@Nullable Player player)
    {
        ServerPlayer serverPlayer = ((ServerPlayer) player);
        SpinjitzuData spinjitzu = serverPlayer.getData(MinejagoAttachmentTypes.SPINJITZU);
        MinejagoEntityEvents.stopSpinjitzu(spinjitzu, serverPlayer, false);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
