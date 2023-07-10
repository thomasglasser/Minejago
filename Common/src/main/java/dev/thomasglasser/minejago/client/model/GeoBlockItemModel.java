package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.item.GeoBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class GeoBlockItemModel extends DefaultedItemGeoModel<GeoBlockItem> {
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
    public GeoBlockItemModel(ResourceLocation assetSubpath) {
        super(assetSubpath);
    }

    @Override
    protected String subtype() {
        return "block";
    }
}
