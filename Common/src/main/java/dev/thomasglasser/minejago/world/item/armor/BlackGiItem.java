package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.BlackGiRenderer;
import dev.thomasglasser.minejago.world.item.IFabricGeoItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BlackGiItem extends ArmorItem implements IGeoArmorItem, IFabricGeoItem {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public BlackGiItem(ArmorMaterial pMaterial, Type type, Properties pProperties) {
        super(pMaterial, type, pProperties);
    }

    @Override
    public GeoArmorRenderer newRenderer() {
        return new BlackGiRenderer();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean isSkintight() {
        return true;
    }

    @Override
    public boolean isGi() {
        return true;
    }
}
