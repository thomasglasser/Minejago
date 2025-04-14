package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.element.Element;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class GiUtils {
    public static final ResourceLocation STANDARD_MODEL_LOCATION = Minejago.modLoc("armor/standard_gi");
    public static final ResourceLocation SYMBOLED_MODEL_LOCATION = Minejago.modLoc("armor/symboled_gi");

    public static ResourceLocation getTextureLocation(String name) {
        return Minejago.modLoc("textures/entity/equipment/humanoid/gi/" + name + ".png");
    }

    public static ResourceLocation getElementalTextureLocation(String name, ResourceKey<Element> element) {
        return element.location().withPrefix("textures/entity/equipment/humanoid/gi/" + name + "_").withSuffix(".png");
    }
}
