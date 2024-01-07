package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.BlockEntityHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ForgeBlockEntityHelper implements BlockEntityHelper {
    @Override
    public void handleUpdateTag(BlockEntity be, CompoundTag tag) {
        be.handleUpdateTag(tag);
    }
}
