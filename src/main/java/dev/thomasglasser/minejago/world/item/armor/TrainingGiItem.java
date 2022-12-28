package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.TrainingGiRenderer;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TrainingGiItem extends PoweredArmorItem
{
    public TrainingGiItem(String powerName, ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(powerName, pMaterial, pSlot, pProperties);
    }

    @Override
    protected GeoArmorRenderer newRenderer() {
        return new TrainingGiRenderer();
    }
}
