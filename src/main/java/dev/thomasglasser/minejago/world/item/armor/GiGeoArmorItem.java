package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.tommylib.api.world.item.armor.GeoArmorItem;

public interface GiGeoArmorItem extends GeoArmorItem {
    default boolean isSkintight() {
        return true;
    }
}
