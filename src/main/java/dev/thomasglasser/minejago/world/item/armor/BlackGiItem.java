package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.BlackGiRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BlackGiItem extends ModeledArmorItem {
    public BlackGiItem(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    protected GeoArmorRenderer newRenderer() {
        return new BlackGiRenderer();
    }
}
