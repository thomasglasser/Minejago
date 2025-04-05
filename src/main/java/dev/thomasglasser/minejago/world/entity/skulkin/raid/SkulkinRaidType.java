package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

public record SkulkinRaidType(TriFunction<ServerLevel, Integer, BlockPos, AbstractSkulkinRaid> createConstructor, BiFunction<ServerLevel, CompoundTag, AbstractSkulkinRaid> loadConstructor, BiFunction<ServerLevel, BlockPos, @Nullable BlockPos> centerFunction) {
    public AbstractSkulkinRaid create(ServerLevel level, Integer id, BlockPos center) {
        return this.createConstructor.apply(level, id, center);
    }

    public AbstractSkulkinRaid load(ServerLevel level, CompoundTag compound) {
        return this.loadConstructor.apply(level, compound);
    }

    public @Nullable BlockPos findValidRaidCenter(ServerLevel level, BlockPos pos) {
        return this.centerFunction.apply(level, pos);
    }
}
