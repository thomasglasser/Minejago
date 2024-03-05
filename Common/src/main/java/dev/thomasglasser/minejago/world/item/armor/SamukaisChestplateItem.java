package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.SamukaisChestplateRenderer;
import dev.thomasglasser.tommylib.api.world.item.armor.GeoArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SamukaisChestplateItem extends ArmorItem implements GeoArmorItem
{
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SamukaisChestplateItem(ArmorMaterial armorMaterial, Properties properties) {
        super(armorMaterial, Type.CHESTPLATE, properties);
    }

    @Override
    public GeoArmorRenderer newRenderer() {
        return new SamukaisChestplateRenderer();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean isSkintight() {
        return false;
    }
}
