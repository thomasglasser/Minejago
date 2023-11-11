package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullTruck;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SkullTruckModel<T extends SkullTruck> extends DefaultedEntityGeoModel<T>
{
	public SkullTruckModel() {
		super(Minejago.modLoc("skulkin/skull_truck"), false);
	}
}
