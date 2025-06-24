package dev.thomasglasser.minejago.world.entity.projectile;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThrownShurikenOfIce extends AbstractArrow {
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownShurikenOfIce.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ID_SNOW_MODE = SynchedEntityData.defineId(ThrownShurikenOfIce.class, EntityDataSerializers.BOOLEAN);

    private boolean dealtDamage;

    public ThrownShurikenOfIce(EntityType<? extends ThrownShurikenOfIce> entity, Level level) {
        super(entity, level);
    }

    public ThrownShurikenOfIce(Level level, LivingEntity shooter, ItemStack pickupItemStack, boolean snowMode) {
        super(MinejagoEntityTypes.THROWN_SHURIKEN_OF_ICE.get(), shooter, level, pickupItemStack, null);
        this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());
        this.entityData.set(ID_SNOW_MODE, snowMode);
    }

    public ThrownShurikenOfIce(Level level, double x, double y, double z, ItemStack pickupItemStack, boolean snowMode) {
        super(MinejagoEntityTypes.THROWN_SHURIKEN_OF_ICE.get(), x, y, z, level, pickupItemStack, pickupItemStack);
        this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());
        this.entityData.set(ID_SNOW_MODE, snowMode);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_FOIL, false);
        builder.define(ID_SNOW_MODE, false);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        if (this.tickCount < 4) {
            super.tick();
            return;
        }

        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        } else if (!this.dealtDamage && this.tickCount >= SharedConstants.TICKS_PER_SECOND * 2) {
            this.dealtDamage = true;
        }

        Entity entity = this.getOwner();
        if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (!this.isAcceptableReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(0.05D)));
            }
        }

        if (level() instanceof ServerLevel serverLevel && this.isSnowMode() && this.tickCount % 2 == 0) {
            BlockPos pos = serverLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPosition());
            BlockState state = serverLevel.getBlockState(pos);
            if (!state.is(Blocks.SNOW) || ((SnowLayerBlock) Blocks.SNOW).canSurvive(state, serverLevel, pos))
                FallingBlockEntity.fall(this.level(), this.blockPosition(), Blocks.SNOW.defaultBlockState());
            serverLevel.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 10, 0.0D, 0.0D, 0.0D, 0.0D);
        }

        super.tick();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.0D;
    }

    private boolean isAcceptableReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    public boolean isFoil() {
        return this.entityData.get(ID_FOIL);
    }

    public boolean isSnowMode() {
        return this.entityData.get(ID_SNOW_MODE);
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return super.findHitEntity(pStartVec, pEndVec);
    }

    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        float f = 30.0F;
        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().arrow(this, entity1 == null ? this : entity1);
        if (this.level() instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, getPickupItem(), entity, damagesource, f);
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
                livingentity.addEffect(new MobEffectInstance(MinejagoMobEffects.FROZEN.asReferenceFrom(livingentity.level().registryAccess()), -1, 0));
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(getDefaultHitGroundSoundEvent());
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            BlockPos pos = result.getBlockPos();
            for (int i = -1; i <= 1; i++) {
                for (int k = -1; k <= 1; k++) {
                    BlockPos blockpos = pos.offset(i, 0, k).above();
                    if (level().canSeeSky(blockpos))
                        blockpos = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos.offset(i, 0, k));
                    if (level().getBlockState(blockpos).canBeReplaced() && !level().getBlockState(blockpos.below()).is(Blocks.ICE)) {
                        level().setBlockAndUpdate(blockpos, Blocks.ICE.defaultBlockState());
                    }
                }
            }
        }
    }

    protected boolean tryPickup(Player player) {
        return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return MinejagoItems.SHURIKEN_OF_ICE.toStack();
    }

    public void playerTouch(Player pEntity) {
        if (!pEntity.swinging && !pEntity.getAbilities().invulnerable) {
            onHitEntity(new EntityHitResult(pEntity));
        } else if (this.ownedBy(pEntity) || this.getOwner() == null) {
            super.playerTouch(pEntity);
        }
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.dealtDamage = pCompound.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("DealtDamage", this.dealtDamage);
    }

    public void tickDespawn() {}

    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return MinejagoSoundEvents.SHURIKEN_OF_ICE_IMPACT.get();
    }
}
