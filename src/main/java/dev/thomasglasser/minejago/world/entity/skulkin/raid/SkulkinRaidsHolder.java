package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface SkulkinRaidsHolder {
    SkulkinRaids minejago$getSkulkinRaids();

    @Nullable
    default AbstractSkulkinRaid getSkulkinRaidAt(BlockPos pos) {
        return this.minejago$getSkulkinRaids().getNearbySkulkinRaid(pos, AbstractSkulkinRaid.VALID_RAID_RADIUS_SQR);
    }

    static SkulkinRaidsHolder of(Object object) {
        if (object instanceof SkulkinRaidsHolder skulkinRaidsHolder) {
            return skulkinRaidsHolder;
        }
        throw new IllegalArgumentException("Object" + object + " is not a SkulkinRaidsHolder");
    }
}
