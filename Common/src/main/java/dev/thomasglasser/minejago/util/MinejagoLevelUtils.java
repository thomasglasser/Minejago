package dev.thomasglasser.minejago.util;

import dev.thomasglasser.minejago.data.tags.MinejagoBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

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

    public static void beamParticles(ParticleOptions particleOptions, Level level, Entity entity)
    {
        if (entity.level() instanceof ServerLevel serverLevel)
        {
            Vec3 look = entity.getViewVector(0);
            Vec3 eyepos = entity.getEyePosition(0).add(look.x * entity.getBbWidth(),0, look.z * entity.getBbWidth());
            for (double i = 0; i <= 200d; i += 0.1d) {
                Vec3 traceVec2 = eyepos.add(look.x * i, look.y * i, look.z * i);
                Vec3 b = new Vec3(traceVec2.x, traceVec2.y, traceVec2.z);
                for (int j = 0; j < 3; ++j) {
                    double d1 = 0.0D;
                    double d2 = level.getRandom().nextGaussian() * 0.02D;
                    double d3 = level.getRandom().nextGaussian() * 0.02D;
                    double d4 = level.getRandom().nextGaussian() * 0.02D;
                    double d6 = b.x();
                    double d7 = b.y();
                    double d8 = b.z();
                    serverLevel.sendParticles(particleOptions, d6, d7, d8, 1, d2, d3, d4, 0);

                }
            }
        }
    }
}
