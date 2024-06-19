package dev.thomasglasser.minejago.world.entity.character;

import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;

public class Cole extends Character {
    private static final EntityDataAccessor<Boolean> DATA_CLIMBING = SynchedEntityData.defineId(Cole.class, EntityDataSerializers.BOOLEAN);

    public Cole(EntityType<? extends Cole> entityType, Level level) {
        super(entityType, level);
        new PowerData(MinejagoPowers.EARTH, true).save(this);
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new WallClimberNavigation(this, pLevel);
    }

    @Override
    public PathNavigation getNavigation() {
        return super.getNavigation();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.IS_FALL))
            return super.hurt(pSource, pAmount / 2.0F);
        return super.hurt(pSource, pAmount);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Character.createAttributes()
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.ARMOR, 1.0F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(DATA_CLIMBING, false);
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return this.entityData.get(DATA_CLIMBING);
    }

    public void setClimbing(boolean pClimbing) {
        this.entityData.set(DATA_CLIMBING, pClimbing);
    }
}