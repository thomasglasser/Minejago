package dev.thomasglasser.minejago.world.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public abstract class PoweredArmorItem extends ArmorItem implements IModeledArmorItem
{
    private String powerName;

    public PoweredArmorItem(String powerName, ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
        this.powerName = powerName;
    }

    public String getPowerName()
    {
        return powerName;
    }
}
