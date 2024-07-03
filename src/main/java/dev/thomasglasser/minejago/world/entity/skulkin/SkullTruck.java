package dev.thomasglasser.minejago.world.entity.skulkin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

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
}
