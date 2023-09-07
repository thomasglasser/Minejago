package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DragonRenderer<T extends Dragon> extends GeoEntityRenderer<T>
{
    public DragonRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }
}
