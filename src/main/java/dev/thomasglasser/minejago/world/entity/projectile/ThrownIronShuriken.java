package dev.thomasglasser.minejago.world.entity.projectile;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThrownIronShuriken extends AbstractArrow {
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownIronShuriken.class, EntityDataSerializers.BOOLEAN);

    private static final byte FLAG_DEALT_DAMAGE = 100;

    private boolean dealtDamage;

    private Vec3 pos;

    public ThrownIronShuriken(EntityType<? extends ThrownIronShuriken> entity, Level level) {
        super(entity, level);
    }

    public ThrownIronShuriken(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(MinejagoEntityTypes.THROWN_IRON_SHURIKEN.get(), pShooter, pLevel, pStack, null);
        this.entityData.set(ID_FOIL, pStack.hasFoil());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_FOIL, false);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        if (pos == null)
            pos = this.position();

        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
            level().broadcastEntityEvent(this, FLAG_DEALT_DAMAGE);
        }

        if (!this.dealtDamage && this.tickCount > 40) {
            Vec3 vec3 = pos.subtract(this.position());
            this.setPos(this.getX(), this.getY() + vec3.y * 0.015D, this.getZ());
            if (this.level().isClientSide) {
                this.yOld = this.getY();
            }

            double d0 = 0.05D;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));

            if (this.position().closerThan(pos, 2)) {
                this.dealtDamage = true;
            }
        } else {
            this.setNoGravity(!this.dealtDamage);
        }

        super.tick();
    }

    public boolean isFoil() {
        return this.entityData.get(ID_FOIL);
    }

    /**
     * Gets the EntityHitResult representing the entity hit
     */
    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return this.dealtDamage ? null : super.findHitEntity(pStartVec, pEndVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        float f = 8.0F;
        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, entity1 == null ? this : entity1);
        if (this.level() instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
        }

        this.dealtDamage = true;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.level() instanceof ServerLevel serverlevel1) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingentity) {
                this.doKnockback(livingentity, damagesource);
                this.doPostHurtEffects(livingentity);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(getDefaultHitGroundSoundEvent(), 1.0F, 1.0F);
    }

    protected boolean tryPickup(Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return MinejagoItems.IRON_SHURIKEN.get().getDefaultInstance();
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(Player pEntity) {
        if (this.tickCount > 3) {
            if (!(pEntity.swinging || inGround))
                onHitEntity(new EntityHitResult(pEntity));
            if (!this.level().isClientSide()) {
                if (tickCount > 40) {
                    this.setNoPhysics(true);
                }
                super.playerTouch(pEntity);
            }
        } else {
            super.playerTouch(pEntity);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.dealtDamage = pCompound.getBoolean("DealtDamage");
        this.pos = NbtUtils.readBlockPos(pCompound, "TargetPos").orElse(new BlockPos(0, 0, 0)).getCenter();
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("DealtDamage", this.dealtDamage);
        pCompound.put("TargetPos", NbtUtils.writeBlockPos(BlockPos.containing(pos)));
    }

    public void tickDespawn() {
        if (this.pickup != Pickup.ALLOWED) {
            super.tickDespawn();
        }
    }

    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }

    public boolean hasDealtDamage() {
        return dealtDamage;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == FLAG_DEALT_DAMAGE) {
            this.dealtDamage = true;
        } else
            super.handleEntityEvent(id);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return MinejagoSoundEvents.SHURIKEN_IMPACT.get();
    }

    @Override
    protected void onInsideBlock(BlockState state) {
        if (state.is(MinejagoBlockTags.SHURIKEN_BREAKS))
            level().destroyBlock(blockPosition(), true, this.getOwner());
        if (level().getBlockState(blockPosition().above()).is(MinejagoBlockTags.SHURIKEN_BREAKS))
            level().destroyBlock(blockPosition().above(), true, this.getOwner());
        if (level().getBlockState(blockPosition().below()).is(MinejagoBlockTags.SHURIKEN_BREAKS))
            level().destroyBlock(blockPosition().below(), true, this.getOwner());
    }
}
