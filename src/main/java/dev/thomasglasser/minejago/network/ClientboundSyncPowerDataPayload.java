package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public record ClientboundSyncPowerDataPayload(int id, PowerData data) implements ExtendedPacketPayload {
    public static final Type<ClientboundSyncPowerDataPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_sync_power_data"));
    public static final StreamCodec<ByteBuf, ClientboundSyncPowerDataPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClientboundSyncPowerDataPayload::id,
            PowerData.STREAM_CODEC, ClientboundSyncPowerDataPayload::data,
            ClientboundSyncPowerDataPayload::new);

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
