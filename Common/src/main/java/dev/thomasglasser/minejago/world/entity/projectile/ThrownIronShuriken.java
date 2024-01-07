package dev.thomasglasser.minejago.world.entity.projectile;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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

import javax.annotation.Nullable;

public class ThrownIronShuriken extends AbstractArrow
{
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownIronShuriken.class, EntityDataSerializers.BOOLEAN);

    private boolean dealtDamage;
    private boolean returned = false;

    private Vec3 pos;

    public ThrownIronShuriken(EntityType<? extends ThrownIronShuriken> entity, Level level) {
        super(entity, level, new ItemStack(MinejagoItems.IRON_SHURIKEN.get()));
    }

    public ThrownIronShuriken(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(MinejagoEntityTypes.THROWN_IRON_SHURIKEN.get(), pShooter, pLevel, pStack);
        this.entityData.set(ID_FOIL, pStack.hasFoil());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_FOIL, false);
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
            level().broadcastEntityEvent(this, (byte) 100);
        }

        if (!this.dealtDamage && this.tickCount > 40 && !returned)
        {
            Vec3 vec3 = pos.subtract(this.position());
            this.setPos(this.getX(), this.getY() + vec3.y * 0.015D, this.getZ());
            if (this.level().isClientSide) {
                this.yOld = this.getY();
            }

            double d0 = 0.05D;
            this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));

            if (this.position().closerThan(pos, 2))
            {
//                this.setNoGravity(false);
//                do {
//                    pos = pos.subtract(0, 1, 0);
//                } while (level().getBlockState(BlockPos.containing(pos)).isAir());
                returned = true;
            }
        }
        else {
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
        if (!(entity instanceof AbstractArrow))
        {
            float f = 8.0F;
            if (entity instanceof LivingEntity livingentity)
            {
                f += EnchantmentHelper.getDamageBonus(getPickupItem(), livingentity.getMobType());
            }

            Entity entity1 = this.getOwner();
            DamageSource damagesource = damageSources().trident(this, (entity1 == null ? this : entity1));
            this.dealtDamage = true;
            this.setNoGravity(false);
            if (entity.hurt(damagesource, f))
            {
                if (entity.getType() == EntityType.ENDERMAN)
                {
                    return;
                }

                if (entity instanceof LivingEntity livingentity1)
                {
                    if (entity1 instanceof LivingEntity)
                    {
                        EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
                        EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity1);
                    }

                    this.doPostHurtEffects(livingentity1);
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
            this.playSound(getDefaultHitGroundSoundEvent());
        }
    }

    protected boolean tryPickup(Player p_150196_) {
        return super.tryPickup(p_150196_) || this.isNoPhysics() && this.ownedBy(p_150196_) && p_150196_.getInventory().add(this.getPickupItem());
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(Player pEntity) {
        if (this.tickCount > 3)
        {
            if (!(pEntity.swinging || inGround))
                onHitEntity(new EntityHitResult(this.getOwner() == null ? this : this.getOwner()));
            if (!this.level().isClientSide()) {
                if (tickCount > 40)
                {
                    this.setNoPhysics(true);
                }
                super.playerTouch(pEntity);
            }
        }
        else
        {
            super.playerTouch(pEntity);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.dealtDamage = pCompound.getBoolean("DealtDamage");
//        this.pos = NbtUtils.readBlockPos(pCompound.getCompound("Pos")).getCenter();
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("DealtDamage", this.dealtDamage);
//        pCompound.put("Pos", NbtUtils.writeBlockPos(BlockPos.containing(pos)));
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
        if (id == 100)
        {
            this.dealtDamage = true;
        }
        else
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
        super.onInsideBlock(state);
    }
}
