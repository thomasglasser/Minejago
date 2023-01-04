package dev.thomasglasser.minejago.client.renderer.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.WoodenNunchucksItem;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class WoodenNunchucksRenderer extends GeoItemRenderer<WoodenNunchucksItem> {
    public WoodenNunchucksRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("nunchucks")));
    }

    @Override
    public ResourceLocation getTextureLocation(WoodenNunchucksItem animatable) {
        return Minejago.modLoc("textures/item/geo/wooden_nunchucks.png");
    }

    private static boolean renderNormal(ItemTransforms.TransformType transformType) {
        return transformType == ItemTransforms.TransformType.FIXED || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.GUI;
    }
}
