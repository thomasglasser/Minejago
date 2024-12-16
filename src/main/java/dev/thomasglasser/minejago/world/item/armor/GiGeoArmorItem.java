package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.tommylib.api.world.item.armor.GeoArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class GiGeoArmorItem extends ArmorItem implements GeoArmorItem {
    protected GiGeoArmorItem(Holder<ArmorMaterial> material, Type type, Item.Properties properties) {
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
