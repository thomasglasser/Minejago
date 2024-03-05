package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.TrainingGiRenderer;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TrainingGiItem extends PoweredArmorItem
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TrainingGiItem(Type type, Properties pProperties) {
        super(MinejagoArmorMaterials.TRAINING_GI, type, pProperties);
    }

    @Override
    public GeoArmorRenderer newRenderer() {
        return new TrainingGiRenderer();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean isSkintight() {
        return true;
    }
}
