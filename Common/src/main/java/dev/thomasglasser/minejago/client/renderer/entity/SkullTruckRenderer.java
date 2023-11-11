package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.client.model.SkullTruckModel;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullTruck;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SkullTruckRenderer<T extends SkullTruck> extends GeoEntityRenderer<T>
{
	public SkullTruckRenderer(EntityRendererProvider.Context renderManager)
	{
		super(renderManager, new SkullTruckModel<>());
	}
}
