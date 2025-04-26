package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.entity.GoldenWeaponHolder;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class DragonHeadModel extends DefaultedEntityGeoModel<GoldenWeaponHolder> {
    private ResourceLocation texture;

    public DragonHeadModel(ResourceLocation assetSubpath) {
        super(assetSubpath);
    }

    @Override
    public ResourceLocation getTextureResource(GoldenWeaponHolder animatable) {
        if (texture == null) texture = animatable.getToSpawnType().getKey().location().withPrefix("textures/entity/dragon/").withSuffix(".png");
        return texture;
    }
}
