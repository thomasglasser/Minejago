package dev.thomasglasser.minejago.world.entity.skulkin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class SkullMotorbike extends AbstractSkulkinVehicle
{
	public SkullMotorbike(EntityType<? extends SkullMotorbike> entityType, Level level)
	{
		super(entityType, level);
	}

	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return this.getPassengers().size() < this.getMaxPassengers();
	}

	@Override
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float scale)
	{
		return new Vector3f(0, dimensions.height - 0.4f, 0);
	}
}
