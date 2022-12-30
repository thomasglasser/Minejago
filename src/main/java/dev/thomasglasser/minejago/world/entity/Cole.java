package dev.thomasglasser.minejago.world.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class Cole extends Character {
    public Cole(EntityType<? extends Cole> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FALL)
            return super.hurt(pSource, pAmount / 2.0F);
        return super.hurt(pSource, pAmount);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Character.createAttributes()
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.ARMOR, 1.0F);
    }
}
