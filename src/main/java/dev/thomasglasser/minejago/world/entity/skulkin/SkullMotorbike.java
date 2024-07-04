package dev.thomasglasser.minejago.world.entity.skulkin;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SkullMotorbike extends AbstractSkulkinVehicle {
    public SkullMotorbike(EntityType<? extends SkullMotorbike> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        playSound(MinejagoSoundEvents.SKULL_MOTORBIKE_IGNITION.get());
        return super.startRiding(entity, force);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (hasControllingPassenger()) {
            return MinejagoSoundEvents.SKULL_MOTORBIKE_AMBIENT_ACTIVE.get();
        } else {
            return MinejagoSoundEvents.SKULL_MOTORBIKE_AMBIENT_IDLE.get();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MinejagoSoundEvents.SKULL_MOTORBIKE_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return MinejagoSoundEvents.SKULL_MOTORBIKE_HURT.get();
    }
}
