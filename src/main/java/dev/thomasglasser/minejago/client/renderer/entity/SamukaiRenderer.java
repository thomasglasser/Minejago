package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.Samukai;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SamukaiRenderer extends GeoEntityRenderer<Samukai> {
    public SamukaiRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(Minejago.modLoc("skulkin/samukai"), true));
    }
}
