package dev.thomasglasser.minejago.world.entity.projectile;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class EarthBlast extends AbstractHurtingProjectile
{
	private int variant = random.nextInt(4);;

	public EarthBlast(EntityType<? extends EarthBlast> entityType, Level level)
	{
		super(entityType, level);
	}

	public EarthBlast(Level level, LivingEntity livingEntity, double d, double e, double f) {
		super(MinejagoEntityTypes.EARTH_BLAST.get(), livingEntity, d, e, f, level);
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		Entity var3 = this.getOwner();
		if (var3 instanceof LivingEntity livingEntity) {
			result.getEntity().hurt(this.damageSources().mobProjectile(this, livingEntity), 6.0F);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		if (!this.level().isClientSide) {
			this.discard();
			level().destroyBlock(result.getBlockPos(), false);
		}
	}

	@Override
	protected boolean shouldBurn()
	{
		return false;
	}

	public int getVariant()
	{
		return variant;
	}
}
