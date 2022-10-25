package dev.thomasglasser.minejago.util;

import dev.thomasglasser.minejago.data.tags.MinejagoBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class MinejagoLevelUtils
{
    private MinejagoLevelUtils() {}

    public static void explode(Level level, LivingEntity entity, Entity source, double x, double y, double z, float r) {
        Explosion e = new Explosion(level, source, x, y, z, r);
        e.explode();
        if (!level.isClientSide())
            for (BlockPos pos : e.getToBlow())
            {
                level.destroyBlock(pos, true, entity, 512);
            }
        e.clearToBlow();
        e.finalizeExplosion(true);
    }

    public static void explode(Level level, LivingEntity entity, double x, double y, double z, float r) {
        explode(level, entity, null, x, y, z, r);
    }

    public static void explode(Level level, LivingEntity entity, Entity source, float r) {
        explode(level, entity, source, entity.getX(), entity.getY(), entity.getZ(), r);
    }

    public static void explode(Level level, LivingEntity entity, float r) {
        explode(level, entity, null, entity.getX(), entity.getY(), entity.getZ(), r);
    }

    public static void explode(Level level, LivingEntity entity, BlockPos pos, float r) {
        explode(level, entity, null, pos.getX(), pos.getY(), pos.getZ(), r);
    }

    public static void explode(Level level, LivingEntity entity, Entity source, BlockPos pos, float r) {
        explode(level, entity, source, pos.getX(), pos.getY(), pos.getZ(), r);
    }

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
