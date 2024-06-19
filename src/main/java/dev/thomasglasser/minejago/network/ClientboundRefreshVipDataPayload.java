package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public record ClientboundRefreshVipDataPayload() implements ExtendedPacketPayload
{
    public static final ClientboundRefreshVipDataPayload INSTANCE = new ClientboundRefreshVipDataPayload();

    public static final Type<ClientboundRefreshVipDataPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_refresh_vip_data"));
    public static final StreamCodec<ByteBuf, ClientboundRefreshVipDataPayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON CLIENT
    public void handle(@Nullable Player player) {
        MinejagoClientUtils.refreshVip();
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
