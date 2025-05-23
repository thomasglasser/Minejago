package dev.thomasglasser.minejago.client.renderer.entity.layers;

import dev.thomasglasser.tommylib.api.network.NetworkUtils;
import dev.thomasglasser.tommylib.api.world.entity.player.SpecialPlayerUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Set;
import java.util.UUID;

public record VipData(SnapshotTesterCosmeticOptions choice, boolean displaySnapshot, boolean displayDev, boolean displayLegacyDev) {
    public static final StreamCodec<FriendlyByteBuf, VipData> STREAM_CODEC = StreamCodec.composite(
            NetworkUtils.enumCodec(SnapshotTesterCosmeticOptions.class), VipData::choice,
            ByteBufCodecs.BOOL, VipData::displaySnapshot,
            ByteBufCodecs.BOOL, VipData::displayDev,
            ByteBufCodecs.BOOL, VipData::displayLegacyDev,
            VipData::new);

    public static final String GIST = "thomasglasser/281c3473f07430ddb83aac3e357a7797";

    public VipData verify(UUID uuid) {
        Set<String> types = SpecialPlayerUtils.getSpecialTypes(GIST, uuid);
        return new VipData(choice, displaySnapshot && types.contains(SpecialPlayerUtils.SNAPSHOT_TESTER_KEY), displayDev && types.contains(SpecialPlayerUtils.DEV_KEY), displayLegacyDev && types.contains(SpecialPlayerUtils.LEGACY_DEV_KEY));
    }
}
