package dev.thomasglasser.minejago.client.renderer.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.NunchucksItem;
import dev.thomasglasser.tommylib.api.client.renderer.item.PerspectiveAwareGeoItemRenderer;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class WoodenNunchucksRenderer extends PerspectiveAwareGeoItemRenderer<NunchucksItem> {
    public WoodenNunchucksRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("nunchucks")), Minejago.modLoc("wooden_nunchucks"));
    }
}
