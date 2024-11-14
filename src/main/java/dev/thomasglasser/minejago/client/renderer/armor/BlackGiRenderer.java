package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.armor.BlackGiItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BlackGiRenderer extends GeoArmorRenderer<BlackGiItem> {
    public BlackGiRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("armor/black_gi")));
    }

    @Override
    public ResourceLocation getTextureLocation(BlackGiItem animatable) {
        return Minejago.modLoc("textures/entity/equipment/humanoid/gi/black.png");
    }
}
