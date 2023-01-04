package dev.thomasglasser.minejago.util;

import dev.thomasglasser.minejago.data.tags.MinejagoBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class MinejagoLevelUtils
{
    private MinejagoLevelUtils() {}

    public static void safeFallSelf(Level level, BlockPos pos)
    {
        BlockState state = level.getBlockState(pos);
        if (!state.is(MinejagoBlockTags.UNBREAKABLE))
            FallingBlockEntity.fall(level, pos, state);
    }

    public static void safeDestroy(Level level, BlockPos pos, boolean drop)
    {
        if (!level.getBlockState(pos).is(MinejagoBlockTags.UNBREAKABLE))
        {
            level.destroyBlock(pos, drop);
        }
    }
}
