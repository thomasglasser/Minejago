package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

public record SkulkinRaidType(Creator creator, Loader loader, CenterFinder centerFinder) {

    public AbstractSkulkinRaid create(ServerLevel level, Integer id, BlockPos center) {
        return this.creator.create(level, id, center);
    }

    public AbstractSkulkinRaid load(ServerLevel level, CompoundTag compound) {
        return this.loader.load(level, compound);
    }

    public @Nullable BlockPos findValidRaidCenter(ServerLevel level, BlockPos pos) {
        return this.centerFinder.findValidRaidCenter(level, pos);
    }
    @FunctionalInterface
    public interface Creator {
        AbstractSkulkinRaid create(ServerLevel level, Integer id, BlockPos center);
    }

    @FunctionalInterface
    public interface Loader {
        AbstractSkulkinRaid load(ServerLevel level, CompoundTag compound);
    }

    @FunctionalInterface
    public interface CenterFinder {
        @Nullable
        BlockPos findValidRaidCenter(ServerLevel level, BlockPos pos);
    }
}
