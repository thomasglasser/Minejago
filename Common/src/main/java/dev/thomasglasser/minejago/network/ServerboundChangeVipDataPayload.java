package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public record ServerboundChangeVipDataPayload(UUID uuid, VipData vipData) implements ExtendedPacketPayload
{
    public static final Type<ServerboundChangeVipDataPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_change_vip_data"));
    public static final StreamCodec<FriendlyByteBuf, ServerboundChangeVipDataPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ServerboundChangeVipDataPayload::uuid,
            VipData.STREAM_CODEC, ServerboundChangeVipDataPayload::vipData,
            ServerboundChangeVipDataPayload::new
    );

    // On Server
    public void handle(Player player) {
        TommyLibServices.NETWORK.sendToAllClients(new ClientboundChangeVipDataPayload(uuid, vipData), player.getServer());
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
