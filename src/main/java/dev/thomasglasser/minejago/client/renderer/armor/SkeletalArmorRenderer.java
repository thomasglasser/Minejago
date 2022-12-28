package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.armor.SkeletalChestplateItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SkeletalArmorRenderer extends GeoArmorRenderer<SkeletalChestplateItem> {

    public SkeletalArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("armor/skeletal")));
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletalChestplateItem animatable) {
        return Minejago.modLoc("textures/models/armor/skeletal_" + animatable.getVariant().getColor().getName() + ".png");
    }
}
