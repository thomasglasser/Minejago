package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.entity.skulkin.Samukai;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SamukaiModel<T extends Samukai> extends DefaultedEntityGeoModel<T> {
    public SamukaiModel(ResourceLocation assetSubpath) {
        super(assetSubpath, true);
    }
}
