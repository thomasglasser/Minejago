package dev.thomasglasser.minejago.platform.services;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IBlockEntityHelper
{
    void handleUpdateTag(BlockEntity be, CompoundTag tag);
}
