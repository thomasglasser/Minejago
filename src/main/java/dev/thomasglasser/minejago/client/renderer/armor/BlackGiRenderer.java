package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.BlackGiItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BlackGiRenderer extends GeoArmorRenderer<BlackGiItem> {

    public BlackGiRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(Minejago.MOD_ID, "armor/black_gi")));
    }}
