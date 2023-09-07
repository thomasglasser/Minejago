package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class DragonModel<T extends Dragon> extends DefaultedEntityGeoModel<T> {
    public DragonModel(ResourceLocation assetSubpath) {
        super(assetSubpath, true);
    }
}
