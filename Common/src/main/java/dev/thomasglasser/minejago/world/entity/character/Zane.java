package dev.thomasglasser.minejago.world.entity.character;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;

public class Zane extends Character
{
    private int lastAir;

    public Zane(EntityType<? extends Zane> entityType, Level level) {
        super(entityType, level);
        Services.DATA.setPowerData(new PowerData(MinejagoPowers.ICE, true), this);
    }

    @Override
    public boolean shouldFloatToSurfaceOfFluid(Character character) {
        boolean flag = (getAirSupply() > lastAir && getAirSupply() < getMaxAirSupply()) || getAirSupply() <= 0;
        lastAir = getAirSupply();
        return !getBrain().isActive(Activity.IDLE) || flag;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this) && level.isWaterAt(this.blockPosition());
    }

    @Override
    public int getMaxAirSupply() {
        return super.getMaxAirSupply() * 2;
    }

    public static boolean checkSurfaceWaterAnimalSpawnRules(EntityType<? extends Zane> zane, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        int i = level.getSeaLevel();
        int j = i - 13;
        return pos.getY() >= j && pos.getY() <= i && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos above = blockPosition().above(2);
        if (level().getBlockState(above).is(BlockTags.ICE) && this.getAirSupply() == 0)
        {
            level().destroyBlock(above, false, this);
            level().destroyBlock(above.north(), false, this);
            level().destroyBlock(above.north().east(), false, this);
            level().destroyBlock(above.north().west(), false, this);
            level().destroyBlock(above.south(), false, this);
            level().destroyBlock(above.south().east(), false, this);
            level().destroyBlock(above.south().west(), false, this);
            level().destroyBlock(above.east(), false, this);
            level().destroyBlock(above.west(), false, this);
        }
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }
}
