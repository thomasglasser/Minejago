package dev.thomasglasser.minejago.client.renderer.entity.layers;

import dev.thomasglasser.tommylib.api.network.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record VipData(SnapshotTesterCosmeticOptions choice, boolean displaySnapshot, boolean displayDev, boolean displayLegacyDev) {
    public static final StreamCodec<FriendlyByteBuf, VipData> STREAM_CODEC = StreamCodec.composite(
            NetworkUtils.enumCodec(SnapshotTesterCosmeticOptions.class), VipData::choice,
            ByteBufCodecs.BOOL, VipData::displaySnapshot,
            ByteBufCodecs.BOOL, VipData::displayDev,
            ByteBufCodecs.BOOL, VipData::displayLegacyDev,
            VipData::new);
}
