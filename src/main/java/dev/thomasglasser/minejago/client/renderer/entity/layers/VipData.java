package dev.thomasglasser.minejago.client.renderer.entity.layers;

import dev.thomasglasser.tommylib.api.util.TommyLibExtraStreamCodecs;
import dev.thomasglasser.tommylib.api.world.entity.player.SpecialPlayerUtils;
import java.util.Set;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record VipData(BetaTesterCosmeticOptions choice, boolean displayBeta, boolean displayDev, boolean displayLegacyDev) {

    public static final StreamCodec<FriendlyByteBuf, VipData> STREAM_CODEC = StreamCodec.composite(
            TommyLibExtraStreamCodecs.forEnum(BetaTesterCosmeticOptions.class), VipData::choice,
            ByteBufCodecs.BOOL, VipData::displayBeta,
            ByteBufCodecs.BOOL, VipData::displayDev,
            ByteBufCodecs.BOOL, VipData::displayLegacyDev,
            VipData::new);

    public static final String GIST = "thomasglasser/281c3473f07430ddb83aac3e357a7797";
    public VipData verify(UUID uuid) {
        Set<String> types = SpecialPlayerUtils.getSpecialTypes(GIST, uuid);
        return new VipData(choice, displayBeta && types.contains(SpecialPlayerUtils.BETA_TESTER_KEY), displayDev && types.contains(SpecialPlayerUtils.DEV_KEY), displayLegacyDev && types.contains(SpecialPlayerUtils.LEGACY_DEV_KEY));
    }
}
