package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import java.util.List;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record ClientboundSetGlowingTag(List<Integer> entityIds, boolean glowing) implements ExtendedPacketPayload {
    public static final Type<ClientboundSetGlowingTag> TYPE = new Type<>(Minejago.modLoc("clientbound_set_glowing_tag"));
    public static final StreamCodec<ByteBuf, ClientboundSetGlowingTag> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT.apply(ByteBufCodecs.list()), ClientboundSetGlowingTag::entityIds,
            ByteBufCodecs.BOOL, ClientboundSetGlowingTag::glowing,
            ClientboundSetGlowingTag::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        for (int entityId : entityIds) {
            Entity entity = player.level().getEntity(entityId);
            if (entity != null) {
                entity.setGlowingTag(glowing);
                entity.setSharedFlag(Entity.FLAG_GLOWING, glowing);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
