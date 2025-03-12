package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;

public record ServerboundSwitchDimensionPayload() implements ExtendedPacketPayload {
    public static final ServerboundSwitchDimensionPayload INSTANCE = new ServerboundSwitchDimensionPayload();
    public static final Type<ServerboundSwitchDimensionPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_switch_dimension"));
    public static final StreamCodec<ByteBuf, ServerboundSwitchDimensionPayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON SERVER
    @Override
    public void handle(Player player) {
        List<ResourceKey<Level>> levelKeys = new ArrayList<>(player.getServer().levelKeys().stream().toList());
        levelKeys.remove(player.level().dimension());
        player.changeDimension(new DimensionTransition(player.getServer().getLevel(levelKeys.get(player.getRandom().nextInt(levelKeys.size()))), player, DimensionTransition.DO_NOTHING));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
