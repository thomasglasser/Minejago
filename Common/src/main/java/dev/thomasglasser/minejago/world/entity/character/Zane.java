package dev.thomasglasser.minejago.world.entity.character;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;

public class Zane extends Character
{
    public Zane(EntityType<? extends Zane> entityType, Level level) {
        super(entityType, level);
        Services.DATA.setPowerData(new PowerData(MinejagoPowers.ICE, true), this);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        if (pSource.is(DamageTypeTags.IS_DROWNING))
            return true;
        return super.isInvulnerableTo(pSource);
    }

    @Override
    public boolean shouldFloatToSurfaceOfFluid(Character character) {
        return !getBrain().isActive(Activity.IDLE);
    }
}
