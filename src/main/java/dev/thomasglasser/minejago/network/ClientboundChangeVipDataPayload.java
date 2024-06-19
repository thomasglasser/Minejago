package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record ClientboundChangeVipDataPayload(UUID uuid, VipData vipData) implements ExtendedPacketPayload
{
    public static final Type<ClientboundChangeVipDataPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_change_vip_data"));
    public static final StreamCodec<FriendlyByteBuf, ClientboundChangeVipDataPayload> CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ClientboundChangeVipDataPayload::uuid,
            VipData.STREAM_CODEC, ClientboundChangeVipDataPayload::vipData,
            ClientboundChangeVipDataPayload::new
    );

    // ON CLIENT
    @Override
    public void handle(@Nullable Player player)
    {
        MinejagoClientUtils.setVipData(ClientUtils.getClientPlayerByUUID(uuid), vipData);
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
