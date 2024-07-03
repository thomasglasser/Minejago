package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface SkulkinRaidsHolder {
    SkulkinRaids getSkulkinRaids();

    @Nullable
    default SkulkinRaid getSkulkinRaidAt(BlockPos pos) {
        return this.getSkulkinRaids().getNearbySkulkinRaid(pos, 9216);
    }
}
