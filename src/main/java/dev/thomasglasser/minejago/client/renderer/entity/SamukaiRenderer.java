package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.SamukaiModel;
import dev.thomasglasser.minejago.world.entity.skulkin.Samukai;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SamukaiRenderer extends GeoEntityRenderer<Samukai> {
    public SamukaiRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SamukaiModel<>(Minejago.modLoc("skulkin/samukai")));
    }
}
