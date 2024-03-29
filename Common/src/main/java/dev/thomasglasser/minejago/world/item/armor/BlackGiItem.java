package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.BlackGiRenderer;
import dev.thomasglasser.tommylib.api.world.item.armor.BaseGeoArmorItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BlackGiItem extends BaseGeoArmorItem implements GiGeoArmorItem
{
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public BlackGiItem(Type type, Properties pProperties) {
        super(MinejagoArmorMaterials.BLACK_GI, type, pProperties);
    }

    @Override
    public GeoArmorRenderer newRenderer() {
        return new BlackGiRenderer();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
