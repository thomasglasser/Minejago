package dev.thomasglasser.minejago.client.renderer.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.NunchucksOfLightningItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class NunchucksOfLightningRenderer extends GeoItemRenderer<NunchucksOfLightningItem> {
    private static final ResourceLocation TEXTURE = Minejago.modLoc("textures/entity/item/nunchucks_of_lightning.png");

    public NunchucksOfLightningRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("nunchucks")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(NunchucksOfLightningItem animatable) {
        return TEXTURE;
    }
}
