package dev.thomasglasser.minejago.world.entity.skulkin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SkullTruck extends AbstractSkulkinVehicle
{
	public SkullTruck(EntityType<? extends SkullTruck> entityType, Level level)
	{
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return AbstractSkulkinVehicle.createAttributes()
				.add(Attributes.MAX_HEALTH, 100.0)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 2.0)
				.add(Attributes.ARMOR, 4.0f);
	}

	@Override
	protected int getMaxPassengers() {
		return 3;
	}

	@Override
	public Vec3 getPassengerRidingPosition(Entity entity)
	{
		int i = this.getPassengers().indexOf(entity);
		if (i >= 0) {
			boolean bl = i == 0;
			float e = 0f;
			float f = 0F;
			if (this.getPassengers().size() > 1) {
				if (!bl) {
					f = -0.7F;
				}

				if (i == 1)
					e += 0.4f;
				else if (i == 2)
					e -= 0.4f;
			}

			return new Vec3(e, 0.0, f).yRot(-this.yBodyRot * (float) (Math.PI / 180.0));
		}
		return Vec3.ZERO;
	}

	@Override
	protected void positionRider(Entity passenger, MoveFunction callback) {
		Vec3 vec3 = this.getPassengerRidingPosition(passenger);
		callback.accept(passenger, vec3.x, vec3.y + (double)passenger.getMyRidingOffset(this), vec3.z);
	}

	@Override
	public float ridingOffset(Entity entity)
	{
		return -1.1f;
	}
}
