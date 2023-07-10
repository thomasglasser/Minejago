package dev.thomasglasser.minejago.world.item;

import software.bernie.geckolib.animatable.GeoItem;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface FabricGeoItem extends GeoItem
{
    default Supplier<Object> getRenderProvider() {
        return null;
    }
    default void createRenderer(Consumer<Object> consumer) {}
}
