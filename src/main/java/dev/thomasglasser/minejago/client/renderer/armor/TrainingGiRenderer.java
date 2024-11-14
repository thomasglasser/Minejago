package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armor.TrainingGiItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TrainingGiRenderer extends GeoArmorRenderer<TrainingGiItem> {
    public TrainingGiRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("armor/training_gi")));
    }

    @Override
    public ResourceLocation getTextureLocation(TrainingGiItem animatable) {
        ResourceLocation location = currentStack.getOrDefault(MinejagoDataComponents.POWER.get(), MinejagoPowers.NONE).location();
        return location.withPrefix("textures/entity/equipment/humanoid/gi/training_").withSuffix(".png");
    }
}
