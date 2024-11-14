package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.armor.SamukaisChestplateItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SamukaisChestplateRenderer extends GeoArmorRenderer<SamukaisChestplateItem> {
    public SamukaisChestplateRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("armor/samukais_chestplate")));
    }

    @Override
    public ResourceLocation getTextureLocation(SamukaisChestplateItem animatable) {
        return Minejago.modLoc("textures/entity/equipment/humanoid/skeletal/samukai.png");
    }
}
