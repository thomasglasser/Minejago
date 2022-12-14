package dev.thomasglasser.minejago.world.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;

public class Zane extends Character
{
    public Zane(EntityType<? extends Zane> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        if (pSource == DamageSource.DROWN)
            return true;
        return super.isInvulnerableTo(pSource);
    }

    @Override
    public boolean shouldFloatToSurfaceOfFluid(Character character) {
        return !getBrain().isActive(Activity.IDLE);
    }
}
