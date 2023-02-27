package dev.thomasglasser.minejago.world.item.armor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public abstract class PoweredArmorItem extends ArmorItem implements IModeledArmorItem
{
    private ResourceLocation powerId;

    public PoweredArmorItem(ResourceLocation powerId, ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
        this.powerId = powerId;
    }

    public ResourceLocation getPowerId()
    {
        return powerId;
    }
}
