package dev.thomasglasser.minejago.client.renderer.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.NunchucksItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class WoodenNunchucksRenderer extends GeoItemRenderer<NunchucksItem> {
    public WoodenNunchucksRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("nunchucks")));
        addRenderLayer(new BlockAndItemGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(NunchucksItem animatable) {
        return Minejago.modLoc("textures/item/geo/wooden_nunchucks.png");
    }
}
