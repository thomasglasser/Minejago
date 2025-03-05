package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FreezingIceBlock extends HalfTransparentBlock {
    public FreezingIceBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (entity instanceof LivingEntity livingEntity) {
            freeze(livingEntity);
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.fallOn(level, state, pos, entity, fallDistance);
        if (entity instanceof LivingEntity livingEntity) {
            freeze(livingEntity);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (entity instanceof LivingEntity livingEntity) {
            freeze(livingEntity);
        }
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        super.attack(state, level, pos, player);
        freeze(player);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        freeze(player);
        return InteractionResult.SUCCESS;
    }

    protected void freeze(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MinejagoMobEffects.FROZEN.asReferenceFrom(entity.level().registryAccess()), SharedConstants.TICKS_PER_MINUTE, 0));
    }
}
