package dev.thomasglasser.minejago.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class NbtArmorGeoModel<T extends GeoAnimatable> extends DefaultedItemGeoModel<T> {
    private final String nbtKey;
    private ResourceLocation location;

    /**
     * Create a new instance of this model class.<br>
     * The asset path should be the truncated relative path from the base folder.<br>
     * E.G.
     * <pre>{@code
     * 	new ResourceLocation("myMod", "armor/obsidian")
     * }</pre>
     *
     * @param assetSubpath
     */
    public NbtArmorGeoModel(ResourceLocation assetSubpath, String nbtKey) {
        super(assetSubpath);
        this.nbtKey = nbtKey;
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        location = ResourceLocation.of(animationState.getData(DataTickets.ITEMSTACK).getOrCreateTag().getString(nbtKey), ':');
    }

    public ResourceLocation getLocation() {
        return location == null ? new ResourceLocation("") : location;
    }
}
