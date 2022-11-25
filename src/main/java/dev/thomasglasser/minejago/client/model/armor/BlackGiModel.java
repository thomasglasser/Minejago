package dev.thomasglasser.minejago.client.model.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.BlackGiItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class BlackGiModel extends AnimatedGeoModel<BlackGiItem> {

    @Override
    public ResourceLocation getModelResource(BlackGiItem object) {
        return new ResourceLocation(Minejago.MOD_ID, "geo/black_gi.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlackGiItem object) {
        return new ResourceLocation(Minejago.MOD_ID, "textures/models/armor/black_gi.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlackGiItem animatable) {
        return new ResourceLocation("");
    }
}
