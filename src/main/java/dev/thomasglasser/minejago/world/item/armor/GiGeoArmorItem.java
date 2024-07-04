package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.tommylib.api.world.item.armor.BaseGeoArmorItem;
import dev.thomasglasser.tommylib.api.world.item.armor.GeoArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public abstract class GiGeoArmorItem extends BaseGeoArmorItem implements GeoArmorItem {
    protected GiGeoArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
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
