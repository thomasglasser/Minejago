package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.focus.FocusData;
import java.util.EnumMap;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ShadowSource extends Entity implements OwnableEntity {
    private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(ShadowSource.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EnumMap<EquipmentSlot, EntityDataAccessor<ItemStack>> DATA_ITEMS = Util.make(new EnumMap<>(EquipmentSlot.class), map -> {
        for (EquipmentSlot slot : EquipmentSlot.values())
            map.put(slot, SynchedEntityData.defineId(ShadowSource.class, EntityDataSerializers.ITEM_STACK));
    });
    @Nullable
    private final ListTag inventory;
    private LivingEntity owner;

    public ShadowSource(EntityType<? extends ShadowSource> entityType, Level level) {
        super(entityType, level);
        this.inventory = null;
    }

    public ShadowSource(LivingEntity owner) {
        super(MinejagoEntityTypes.SHADOW_SOURCE.get(), owner.level());
        getData(MinejagoAttachmentTypes.FOCUS).setMeditationType(FocusData.MeditationType.MEGA);
        setOwnerUUID(owner.getUUID());
        setPos(owner.position());
        setRot(owner.getXRot(), owner.getYRot());
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            setItemBySlot(slot, owner.getItemBySlot(slot));
            owner.setItemSlot(slot, ItemStack.EMPTY);
        }
        if (owner instanceof Player player) {
            this.inventory = player.getInventory().save(new ListTag());
            player.getInventory().clearContent();
        } else
            this.inventory = null;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            applyGravity();
//            move(MoverType.SELF, this.getDeltaMovement());
        }
    }

    @Override
    public @Nullable UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(uuid));
    }

    public @Nullable ListTag getInventory() {
        return this.inventory;
    }

    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return this.entityData.get(DATA_ITEMS.get(slot));
    }

    public void setItemBySlot(EquipmentSlot slot, ItemStack stack) {
        this.entityData.set(DATA_ITEMS.get(slot), stack);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_OWNER_UUID, Optional.empty());
        for (EquipmentSlot slot : EquipmentSlot.values())
            builder.define(DATA_ITEMS.get(slot), ItemStack.EMPTY);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (getOwner() instanceof ServerPlayer owner) {
            MinejagoEntityEvents.stopShadowForm(owner);
            owner.hurt(source, amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected double getDefaultGravity() {
        LivingEntity owner = this.getOwner();
        return owner != null ? owner.getGravity() : super.getDefaultGravity();
    }

    @Override
    public @Nullable LivingEntity getOwner() {
        if (owner == null || owner.getUUID() != getOwnerUUID()) {
            owner = OwnableEntity.super.getOwner();
        }
        return owner;
    }
}
