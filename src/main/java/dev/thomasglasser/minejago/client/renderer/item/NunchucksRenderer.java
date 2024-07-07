package dev.thomasglasser.minejago.client.renderer.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.NunchucksItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class NunchucksRenderer extends GeoItemRenderer<NunchucksItem> {
    private final ResourceLocation texture;

    public NunchucksRenderer(ResourceLocation itemId) {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("nunchucks")));
        texture = itemId.withPrefix("textures/item/geo/").withSuffix(".png");
    }

    @Override
    public ResourceLocation getTextureLocation(NunchucksItem animatable) {
        return texture;
    }
}
