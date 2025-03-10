package dev.thomasglasser.minejago.world.entity.dragon;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.projectile.EarthBlast;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class EarthDragon extends Dragon {
    public EarthDragon(EntityType<? extends EarthDragon> entityType, Level level) {
        super(entityType, level, MinejagoPowers.EARTH, MinejagoItemTags.EARTH_DRAGON_PROTECTS);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return MinejagoSoundEvents.EARTH_DRAGON_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MinejagoSoundEvents.EARTH_DRAGON_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return MinejagoSoundEvents.EARTH_DRAGON_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(MinejagoSoundEvents.EARTH_DRAGON_STEP.get(), 1.0F, 1.0F);
    }

    @Override
    public @Nullable SoundEvent getFlapSound() {
        return MinejagoSoundEvents.EARTH_DRAGON_FLAP.get();
    }

    @Override
    protected AbstractHurtingProjectile getRangedAttackProjectile(double a, double b, double c) {
        return new EarthBlast(level(), this, a, b, c);
    }

    @Override
    protected SoundEvent getShootSound() {
        return MinejagoSoundEvents.EARTH_DRAGON_ROAR.get();
    }
}
