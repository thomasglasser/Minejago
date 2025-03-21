package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armor.TraineeGiItem;
import net.minecraft.resources.ResourceLocation;

public class DragonExtremeGiRenderer extends TraineeGiRenderer {
    @Override
    public ResourceLocation getTextureLocation(TraineeGiItem animatable) {
        ResourceLocation location = currentStack.getOrDefault(MinejagoDataComponents.POWER.get(), MinejagoPowers.NONE).location();
        return location.withPrefix("textures/entity/equipment/humanoid/gi/dragon_extreme_").withSuffix(".png");
    }
}
