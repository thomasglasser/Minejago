package dev.thomasglasser.minejago.world.entity.shadow;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractShadowCopy extends PathfinderMob implements OwnableEntity {
    private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(AbstractShadowCopy.class, EntityDataSerializers.OPTIONAL_UUID);
    private LivingEntity storedOwner;

    protected AbstractShadowCopy(EntityType<? extends AbstractShadowCopy> entityType, Level level) {
        super(entityType, level);
    }

    public AbstractShadowCopy(EntityType<? extends AbstractShadowCopy> entityType, LivingEntity owner) {
        super(entityType, owner.level());
        setOwnerUUID(owner.getUUID());
        if (owner.level() instanceof ServerLevel serverLevel)
            teleportTo(serverLevel, owner.getX(), owner.getY(), owner.getZ(), Set.of(), owner.getYRot(), owner.getXRot());
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            setItemSlot(slot, owner.getItemBySlot(slot));
        }
        this.getAttributes().assignAllValues(owner.getAttributes());
        if (owner instanceof Player) {
            this.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(0.25);
            this.getAttributes().getInstance(Attributes.SCALE).setBaseValue(0.94);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_OWNER_UUID, Optional.empty());
    }

    @Override
    public @Nullable UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(uuid));
    }

    @Override
    public @Nullable LivingEntity getOwner() {
        if (storedOwner == null || storedOwner.getUUID() != getOwnerUUID()) {
            storedOwner = OwnableEntity.super.getOwner();
        }
        return storedOwner;
    }

    @Override
    public Component getName() {
        LivingEntity owner = this.getOwner();
        if (!hasCustomName() && owner != null) {
            return Component.translatable(this.getType().getDescriptionId(), owner.getName());
        }
        return super.getName();
    }
}
