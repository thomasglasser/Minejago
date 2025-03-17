package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ServerboundRecallClonesPayload() implements ExtendedPacketPayload {
    public static final ServerboundRecallClonesPayload INSTANCE = new ServerboundRecallClonesPayload();
    public static final Type<ServerboundRecallClonesPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_recall_clones"));
    public static final StreamCodec<ByteBuf, ServerboundRecallClonesPayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON SERVER
    @Override
    public void handle(Player player) {
        MinejagoEntityEvents.recallShadowClones(player);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
