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

	public void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
		int i = this.getPassengers().indexOf(pPassenger);
		if (i >= 0) {
			boolean bl = i == 0;
			float e = 0f;
			float f = 0F;
			float g = (float)((this.isRemoved() ? 0.01F : this.getPassengersRidingOffset()) + pPassenger.getMyRidingOffset());
			if (this.getPassengers().size() > 1) {
				if (!bl) {
					f = -0.7F;
				}

				if (i == 1)
					e += 0.4f;
				else if (i == 2)
					e -= 0.4f;
			}

			Vec3 vec3 = new Vec3(e, 0.0, f).yRot(-this.yBodyRot * (float) (Math.PI / 180.0));
			pCallback.accept(pPassenger, this.getX() + vec3.x, this.getY() + (double)g, this.getZ() + vec3.z);
		}
	}

	@Override
	public double getMyRidingOffset()
	{
		return -1.1;
	}
}
