package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.network.codec.ExtraStreamCodecs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ServerboundFlyVehiclePayload(Stage stage) implements ExtendedPacketPayload {
    public static final Type<ServerboundFlyVehiclePayload> TYPE = new Type<>(Minejago.modLoc("serverbound_fly_vehicle"));
    public static final StreamCodec<FriendlyByteBuf, ServerboundFlyVehiclePayload> CODEC = StreamCodec.composite(
            ExtraStreamCodecs.forEnum(Stage.class), ServerboundFlyVehiclePayload::stage,
            ServerboundFlyVehiclePayload::new);

    // On Server
    public void handle(Player player) {
//        if (player.getVehicle() instanceof PlayerRideableFlying flying) {
//            switch (stage) {
//                case START_ASCEND -> flying.ascend();
//                case START_DESCEND -> flying.descend();
//                case STOP -> flying.stop();
//            }
//        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum Stage {
        START_ASCEND,
        START_DESCEND,
        STOP
    }
}
