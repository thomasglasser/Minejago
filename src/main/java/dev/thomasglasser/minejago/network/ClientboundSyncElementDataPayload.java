package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.storage.ElementData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public record ClientboundSyncElementDataPayload(int id, ElementData data) implements ExtendedPacketPayload {
    public static final Type<ClientboundSyncElementDataPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_sync_element_data"));
    public static final StreamCodec<ByteBuf, ClientboundSyncElementDataPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClientboundSyncElementDataPayload::id,
            ElementData.STREAM_CODEC, ClientboundSyncElementDataPayload::data,
            ClientboundSyncElementDataPayload::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        Entity entity = player.level().getEntity(id);
        if (entity instanceof LivingEntity livingEntity)
            data.save(livingEntity, false);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
