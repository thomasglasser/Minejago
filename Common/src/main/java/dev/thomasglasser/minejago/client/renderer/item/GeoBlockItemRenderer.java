package dev.thomasglasser.minejago.client.renderer.item;

import dev.thomasglasser.minejago.client.model.GeoBlockItemModel;
import dev.thomasglasser.minejago.world.item.GeoBlockItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GeoBlockItemRenderer extends GeoItemRenderer<GeoBlockItem> {
    public GeoBlockItemRenderer(GeoBlockItemModel model) {
        super(model);
    }
}
