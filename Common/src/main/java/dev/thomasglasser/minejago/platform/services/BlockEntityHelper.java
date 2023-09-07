package dev.thomasglasser.minejago.platform.services;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface BlockEntityHelper
{
    void handleUpdateTag(BlockEntity be, CompoundTag tag);
}
