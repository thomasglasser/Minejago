package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.entity.PlayerRideableFlying;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;

public class ServerboundFlyVehiclePacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_fly_vehicle_packet");

    private final Type type;

    public ServerboundFlyVehiclePacket(FriendlyByteBuf buf) {
        type = buf.readEnum(Type.class);
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeEnum(type);
    }

    public static FriendlyByteBuf toBytes(Type type) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeEnum(type);

        return buf;
    }

    public void handle(ServerPlayer serverPlayer) {
        if(serverPlayer.getVehicle() instanceof PlayerRideableFlying flying){
            switch (type){
                case START_ASCEND -> flying.ascend();
                case START_DESCEND -> flying.descend();
                case STOP -> flying.stop();
            }
        }
    }

    public enum Type implements StringRepresentable {
        START_ASCEND("ascend"),
        START_DESCEND("descend"),
        STOP("stop");

        final String name;

        Type(String name){
            this.name = name;
        }
        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
