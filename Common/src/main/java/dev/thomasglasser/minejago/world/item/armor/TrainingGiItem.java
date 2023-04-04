package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.TrainingGiRenderer;
import dev.thomasglasser.minejago.world.item.IFabricGeoItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TrainingGiItem extends PoweredArmorItem implements IFabricGeoItem
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TrainingGiItem(ArmorMaterial pMaterial, Type type, Properties pProperties) {
        super(pMaterial, type, pProperties);
    }

    @Override
    public GeoArmorRenderer newRenderer() {
        return new TrainingGiRenderer();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean isSkintight() {
        return true;
    }
}
