package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.element.MinejagoElements;
import dev.thomasglasser.minejago.world.item.armor.TraineeGiArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TraineeGiRenderer extends GeoArmorRenderer<TraineeGiArmorItem> {
    public TraineeGiRenderer() {
        super(new DefaultedItemGeoModel<>(GiUtils.SYMBOLED_MODEL_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(TraineeGiArmorItem animatable) {
        return GiUtils.getElementalTextureLocation("trainee", currentStack.getOrDefault(MinejagoDataComponents.ELEMENT.get(), MinejagoElements.NONE));
    }
}
