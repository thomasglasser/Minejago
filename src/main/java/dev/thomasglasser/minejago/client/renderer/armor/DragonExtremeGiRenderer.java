package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.element.MinejagoElements;
import dev.thomasglasser.minejago.world.item.armor.TraineeGiArmorItem;
import net.minecraft.resources.ResourceLocation;

public class DragonExtremeGiRenderer extends TraineeGiRenderer {
    @Override
    public ResourceLocation getTextureLocation(TraineeGiArmorItem animatable) {
        ResourceLocation location = currentStack.getOrDefault(MinejagoDataComponents.ELEMENT.get(), MinejagoElements.NONE).location();
        return location.withPrefix("textures/entity/equipment/humanoid/gi/dragon_extreme_").withSuffix(".png");
    }
}
