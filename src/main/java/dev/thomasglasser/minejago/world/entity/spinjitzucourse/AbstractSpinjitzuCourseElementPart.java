package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSpinjitzuCourseElementPart<T extends AbstractSpinjitzuCourseElement<T>> extends PartEntity<T> {
    public final String name;
    private final EntityDimensions size;
    protected final double offsetX;
    protected final double offsetY;
    protected final double offsetZ;

    protected boolean active = false;

    public AbstractSpinjitzuCourseElementPart(T parent, String name, float width, float height, double offsetX, double offsetY, double offsetZ) {
        super(parent);
        this.size = EntityDimensions.scalable(width, height);
        this.refreshDimensions();
        this.name = name;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        moveTo(parent.getX() + offsetX, parent.getY() + offsetY, parent.getZ() + offsetZ);
        noCulling = true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    public boolean isPickable() {
        return active;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.getParent().getPickResult();
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return active && !this.isInvulnerableTo(source) && this.getParent().hurt(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return getParent().isInvulnerableTo(source) || super.isInvulnerableTo(source);
    }

    /**
     * Returns {@code true} if Entity argument is equal to this Entity
     */
    @Override
    public boolean is(Entity entity) {
        return this == entity || this.getParent() == entity;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return active ? this.size : EntityDimensions.scalable(0.0f, 0.0f);
    }

    @Override
    public boolean shouldBeSaved() {
        return active;
    }

    @Override
    public void tick() {
        super.tick();
        calculatePosition();
    }

    public abstract void calculatePosition();

    public void setActive(boolean active) {
        this.active = active;
        refreshDimensions();
    }
}
