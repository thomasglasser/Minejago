package dev.thomasglasser.minejago.world.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.level.Level;

public class Wu extends Character {
    public Wu(EntityType<? extends Wu> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Character.createAttributes()
                .add(Attributes.MAX_HEALTH, ((RangedAttribute)Attributes.MAX_HEALTH).getMaxValue())
                .add(Attributes.ATTACK_KNOCKBACK, ((RangedAttribute)Attributes.ATTACK_KNOCKBACK).getMaxValue());
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        if (!(pSource == DamageSource.OUT_OF_WORLD))
            return true;
        return super.isInvulnerableTo(pSource);
    }
}
