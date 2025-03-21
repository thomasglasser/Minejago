package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface SkulkinRaidsHolder {
    SkulkinRaids minejago$getSkulkinRaids();

    @Nullable
    default SkulkinRaid getSkulkinRaidAt(BlockPos pos) {
        return this.minejago$getSkulkinRaids().getNearbySkulkinRaid(pos, SkulkinRaid.VALID_RAID_RADIUS_SQR);
    }
}
