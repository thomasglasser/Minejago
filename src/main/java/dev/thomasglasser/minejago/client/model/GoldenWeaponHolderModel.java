package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.entity.GoldenWeaponHolder;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class GoldenWeaponHolderModel extends DefaultedEntityGeoModel<GoldenWeaponHolder> {
    private final ResourceLocation textureLocation;

    public GoldenWeaponHolderModel(ResourceLocation assetSubpath, ResourceLocation textureLocation) {
        super(assetSubpath);
        this.textureLocation = buildFormattedTexturePath(textureLocation);
    }

    @Override
    public ResourceLocation getTextureResource(GoldenWeaponHolder animatable) {
        return textureLocation;
    }
}
