package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record ClientboundSyncSpinjitzuDataPayload(int id, SpinjitzuData data) implements ExtendedPacketPayload {
    public static final Type<ClientboundSyncSpinjitzuDataPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_sync_spinjitzu_data"));
    public static final StreamCodec<ByteBuf, ClientboundSyncSpinjitzuDataPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClientboundSyncSpinjitzuDataPayload::id,
            SpinjitzuData.STREAM_CODEC, ClientboundSyncSpinjitzuDataPayload::data,
            ClientboundSyncSpinjitzuDataPayload::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        Entity entity = player.level().getEntity(id);
        if (entity != null)
            data.save(entity, false);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
