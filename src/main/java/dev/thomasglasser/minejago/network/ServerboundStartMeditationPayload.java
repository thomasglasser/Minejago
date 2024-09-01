package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public record ServerboundStartMeditationPayload() implements ExtendedPacketPayload {
    public static final ServerboundStartMeditationPayload INSTANCE = new ServerboundStartMeditationPayload();

    public static final Type<ServerboundStartMeditationPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_start_meditation"));
    public static final StreamCodec<ByteBuf, ServerboundStartMeditationPayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON SERVER
    public void handle(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            player.getData(MinejagoAttachmentTypes.FOCUS).startMeditating();
            player.refreshDimensions();
            CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(serverPlayer);
            persistentData.putString("StartPos", serverPlayer.blockPosition().toString());
            TommyLibServices.ENTITY.setPersistentData(serverPlayer, persistentData, false);
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundStartMeditationPayload(serverPlayer.getUUID()), serverPlayer.getServer());
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
