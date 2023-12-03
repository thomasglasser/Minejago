package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.AbstractSkulkinVehicle;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SkullMotorbikeRenderer<T extends AbstractSkulkinVehicle> extends GeoEntityRenderer<T>
{
	public SkullMotorbikeRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new DefaultedEntityGeoModel<>(Minejago.modLoc("skulkin/skull_motorbike"), false));
	}
}
