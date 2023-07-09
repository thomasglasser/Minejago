package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.world.item.FabricGeoItem;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public interface GeoArmorItem extends GeoItem, FabricGeoItem
{
    GeoArmorRenderer newRenderer();

    @Override
    default void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    AnimatableInstanceCache getAnimatableInstanceCache();

    boolean isSkintight();

    default boolean isGi()
    {
        return false;
    }
}
