package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.entity.PlayerRideableFlying;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ServerboundFlyVehiclePacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_fly_vehicle_packet");

    private final Type type;

    public ServerboundFlyVehiclePacket(FriendlyByteBuf buf) {
        type = buf.readEnum(Type.class);
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeEnum(type);
    }

    public static FriendlyByteBuf write(Type type) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeEnum(type);

        return buf;
    }

    public void handle(@Nullable Player player) {
        if(player.getVehicle() instanceof PlayerRideableFlying flying){
            switch (type){
                case START_ASCEND -> flying.ascend();
                case START_DESCEND -> flying.descend();
                case STOP -> flying.stop();
            }
        }
    }

    @Override
    public Direction direction()
    {
        return Direction.CLIENT_TO_SERVER;
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
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
