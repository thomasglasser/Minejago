package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ServerboundStopShadowFormPayload() implements ExtendedPacketPayload {
    public static final ServerboundStopShadowFormPayload INSTANCE = new ServerboundStopShadowFormPayload();

    public static final Type<ServerboundStopShadowFormPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_stop_shadow_form"));
    public static final StreamCodec<ByteBuf, ServerboundStopShadowFormPayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON SERVER
    public void handle(Player player) {
        player.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).ifPresent(shadowData -> MinejagoEntityEvents.stopShadowForm(player));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
