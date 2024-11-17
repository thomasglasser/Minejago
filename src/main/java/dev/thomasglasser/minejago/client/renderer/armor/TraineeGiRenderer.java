package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armor.TraineeGiItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TraineeGiRenderer extends GeoArmorRenderer<TraineeGiItem> {
    public TraineeGiRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("armor/trainee_gi")));
    }

    @Override
    public ResourceLocation getTextureLocation(TraineeGiItem animatable) {
        ResourceLocation location = currentStack.getOrDefault(MinejagoDataComponents.POWER.get(), MinejagoPowers.NONE).location();
        return location.withPrefix("textures/entity/equipment/humanoid/gi/trainee_").withSuffix(".png");
    }
}
