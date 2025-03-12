package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundStartShadowFormPayload() implements ExtendedPacketPayload {
    public static final ClientboundStartShadowFormPayload INSTANCE = new ClientboundStartShadowFormPayload();
    public static final Type<ClientboundStartShadowFormPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_start_shadow_form"));
    public static final StreamCodec<ByteBuf, ClientboundStartShadowFormPayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        player.setOnGround(false);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
