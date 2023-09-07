package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.GeoBlockItem;
import net.minecraft.resources.ResourceLocation;

public class DragonHeadBlockItemModel extends GeoBlockItemModel {

    private final DragonHeadModel model;
    private final String dragon;
    private final String block;

    public DragonHeadBlockItemModel(DragonHeadModel model, String dragon, String block) {
        super(Minejago.modLoc(""));
        this.model = model;
        this.dragon = dragon;
        this.block = block;
    }

    @Override
    public ResourceLocation getModelResource(GeoBlockItem animatable) {
        return model.getModelResource(block);
    }

    @Override
    public ResourceLocation getTextureResource(GeoBlockItem animatable) {
        return model.getTextureResource(dragon);
    }

    @Override
    public ResourceLocation getAnimationResource(GeoBlockItem animatable) {
        return model.getAnimationResource(block);
    }
}
