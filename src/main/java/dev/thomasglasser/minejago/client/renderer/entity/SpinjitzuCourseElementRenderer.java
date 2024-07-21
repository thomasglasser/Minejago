package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpinjitzuCourseElementRenderer<T extends AbstractSpinjitzuCourseElement<T>> extends GeoEntityRenderer<T> {
    public SpinjitzuCourseElementRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }
}
