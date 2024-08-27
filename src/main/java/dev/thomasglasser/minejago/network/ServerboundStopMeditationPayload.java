package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public record ServerboundStopMeditationPayload(boolean fail) implements ExtendedPacketPayload {
    public static final Type<ServerboundStopMeditationPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_stop_meditation"));
    public static final StreamCodec<ByteBuf, ServerboundStopMeditationPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, ServerboundStopMeditationPayload::fail,
            ServerboundStopMeditationPayload::new);

    public void handle(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            player.getData(MinejagoAttachmentTypes.FOCUS).stopMeditating();
            TommyLibServices.ENTITY.getPersistentData(serverPlayer).remove("StartPos");
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundStopMeditationPayload(serverPlayer.getUUID(), fail), serverPlayer.getServer());
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
