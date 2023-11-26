package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.BlackGiRenderer;
import net.minecraft.world.item.ArmorItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BlackGiItem extends ArmorItem implements GeoArmorItem {
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

    @Override
    public boolean isSkintight() {
        return true;
    }

    @Override
    public boolean isGi() {
        return true;
    }
}
