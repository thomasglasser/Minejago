package dev.thomasglasser.minejago.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MinejagoBrushableBlockEntity extends BrushableBlockEntity {
    public MinejagoBrushableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }
}
