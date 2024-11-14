package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.item.armor.SkeletalChestplateItem;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SkeletalArmorRenderer extends GeoArmorRenderer<SkeletalChestplateItem> {
    private static final Map<Skulkin.Variant, ResourceLocation> TEXTURE_LOCS = new HashMap<>();

    public SkeletalArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("armor/skeletal")));
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletalChestplateItem animatable) {
        if (!TEXTURE_LOCS.containsKey(animatable.getVariant()))
            TEXTURE_LOCS.put(animatable.getVariant(), Minejago.modLoc("textures/entity/equipment/humanoid/skeletal/" + animatable.getVariant().name().toLowerCase() + ".png"));
        return TEXTURE_LOCS.get(animatable.getVariant());
    }
}
