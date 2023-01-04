package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IBlockEntityHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FabricBlockEntityHelper implements IBlockEntityHelper
{

    @Override
    public void handleUpdateTag(BlockEntity be, CompoundTag tag) {}
}
