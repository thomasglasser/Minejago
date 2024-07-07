package dev.thomasglasser.minejago.world.entity.projectile;

import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.tommylib.api.world.entity.projectile.ThrownSword;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

// TODO: Fix behavior
public class ThrownShuriken extends ThrownSword {
    private boolean dealtDamage;

    private Vec3 pos;

    public ThrownShuriken(EntityType<? extends ThrownSword> entity, Level level) {
        super(entity, level);
    }

    public ThrownShuriken(EntityType<? extends ThrownSword> entityType, Level level, LivingEntity shooter, ItemStack pickupItemStack, Holder<SoundEvent> hitGroundSound) {
        super(entityType, level, shooter, pickupItemStack, hitGroundSound);
    }

    public ThrownShuriken(EntityType<? extends ThrownSword> entityType, Level level, double x, double y, double z, ItemStack pickupItemStack, Holder<SoundEvent> hitGroundSound) {
        super(entityType, level, x, y, z, pickupItemStack, hitGroundSound);
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
        this.pos = NbtUtils.readBlockPos(pCompound, "TargetPos").orElse(new BlockPos(0, 0, 0)).getCenter();
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("TargetPos", NbtUtils.writeBlockPos(BlockPos.containing(pos)));
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
