package dev.thomasglasser.minejago.world.entity.skulkin;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SkullTruck extends AbstractSkulkinVehicle {
    public SkullTruck(EntityType<? extends SkullTruck> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractSkulkinVehicle.createAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.ARMOR, 4.0f);
    }

    @Override
    protected int getMaxPassengers() {
        return 3;
    }

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        playSound(MinejagoSoundEvents.SKULL_TRUCK_IGNITION.get());
        return super.startRiding(entity, force);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (hasControllingPassenger()) {
            return MinejagoSoundEvents.SKULL_TRUCK_AMBIENT_ACTIVE.get();
        } else {
            return MinejagoSoundEvents.SKULL_TRUCK_AMBIENT_IDLE.get();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MinejagoSoundEvents.SKULL_TRUCK_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return MinejagoSoundEvents.SKULL_TRUCK_HURT.get();
    }
}
