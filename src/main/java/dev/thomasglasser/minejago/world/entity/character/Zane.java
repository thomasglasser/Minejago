package dev.thomasglasser.minejago.world.entity.character;

import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.tslat.smartbrainlib.util.BrainUtils;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;

public class Zane extends Character {
    private int lastAir;

    public Zane(EntityType<? extends Zane> entityType, Level level) {
        super(entityType, level);
        new PowerData(MinejagoPowers.ICE, true).save(this, false);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    public boolean shouldFloatToSurfaceOfFluid(Character character) {
        boolean flag = (getAirSupply() > lastAir && getAirSupply() < getMaxAirSupply()) || getAirSupply() <= 0;
        lastAir = getAirSupply();
        return !getBrain().isActive(Activity.IDLE) || flag;
    }

    @Override
    public void onStartFloatingToSurfaceOfFluid(Character character) {
        setMeditationStatus(MeditationStatus.FINISHING);
        super.onStartFloatingToSurfaceOfFluid(character);
    }

    @Override
    public void onStopFloatingToSurfaceOfFluid(Character character) {
        if (character.onGround() || character.getAirSupply() < character.getMaxAirSupply() - 60 || BrainUtils.hasMemory(character, MemoryModuleType.ATTACK_TARGET))
            setMeditationStatus(MeditationStatus.NONE);
        else
            setMeditationStatus(MeditationStatus.STARTING);
        super.onStopFloatingToSurfaceOfFluid(character);
    }

    @Override
    public boolean shouldSetRandomWalkTarget(Character character) {
        return !isInWater();
    }

    @Override
    public boolean shouldAvoidWater(Character character) {
        return false;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }

    @Override
    public int getMaxAirSupply() {
        return super.getMaxAirSupply() * 2;
    }

    public static boolean checkZaneSpawnRules(EntityType<Zane> animal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return Character.checkCharacterSpawnRules(Zane.class, animal, level, spawnType, pos, random) && level.getFluidState(pos).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos above = blockPosition().above(2);
        if (level().getBlockState(above).is(BlockTags.ICE) && this.getAirSupply() == 0) {
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "meditation", animationState -> switch (getMeditationStatus()) {
            case STARTING:
                yield animationState.setAndContinue(MEDITATION_START);
            case FLOATING:
                yield animationState.setAndContinue(MEDITATION_FLOAT);
            case FINISHING:
                yield animationState.setAndContinue(MEDITATION_FINISH);
            default:
                yield PlayState.STOP;
        }));
    }
}
