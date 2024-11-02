package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.tommylib.api.world.item.armor.ExtendedArmorItem;
import dev.thomasglasser.tommylib.api.world.item.armor.GeoArmorItem;
import dev.thomasglasser.tommylib.api.world.item.equipment.ExtendedArmorMaterial;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

public abstract class GiGeoArmorItem extends ExtendedArmorItem implements GeoArmorItem {
    protected GiGeoArmorItem(ExtendedArmorMaterial material, ArmorType type, Item.Properties properties) {
        super(material, type, properties);
    }

    public boolean isSkintight() {
        return true;
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
