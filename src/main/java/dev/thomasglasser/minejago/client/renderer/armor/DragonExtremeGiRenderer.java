package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.element.MinejagoElements;
import dev.thomasglasser.minejago.world.item.armor.DragonExtremeGiArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DragonExtremeGiRenderer extends GeoArmorRenderer<DragonExtremeGiArmorItem> {
    public DragonExtremeGiRenderer() {
        super(new DefaultedItemGeoModel<>(GiUtils.STANDARD_MODEL_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(DragonExtremeGiArmorItem animatable) {
        return GiUtils.getElementalTextureLocation("dragon_extreme", currentStack.getOrDefault(MinejagoDataComponents.ELEMENT.get(), MinejagoElements.NONE));
    }
}
