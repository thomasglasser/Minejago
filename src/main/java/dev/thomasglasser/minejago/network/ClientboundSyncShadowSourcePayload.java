package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public record ClientboundSyncShadowSourcePayload(int id, Optional<UUID> uuid) implements ExtendedPacketPayload {
    public static final Type<ClientboundSyncShadowSourcePayload> TYPE = new Type<>(Minejago.modLoc("clientbound_sync_shadow_source"));
    public static final StreamCodec<ByteBuf, ClientboundSyncShadowSourcePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClientboundSyncShadowSourcePayload::id,
            ByteBufCodecs.optional(UUIDUtil.STREAM_CODEC), ClientboundSyncShadowSourcePayload::uuid,
            ClientboundSyncShadowSourcePayload::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        Entity entity = player.level().getEntity(id);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setData(MinejagoAttachmentTypes.SHADOW_SOURCE, uuid);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
