package dev.thomasglasser.minejago.world.entity.skulkin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SkullMotorbike extends AbstractSkulkinVehicle {
    public SkullMotorbike(EntityType<? extends SkullMotorbike> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }
}
