package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.SamukaisChestplateRenderer;
import dev.thomasglasser.tommylib.api.world.item.armor.GeoArmorItem;
import java.util.function.Consumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SamukaisChestplateItem extends ArmorItem implements GeoArmorItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SamukaisChestplateItem(Holder<ArmorMaterial> armorMaterial, Properties properties) {
        super(armorMaterial, ArmorItem.Type.CHESTPLATE, properties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private SamukaisChestplateRenderer renderer;

            @Override
            public @Nullable <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if (this.renderer == null)
                    this.renderer = new SamukaisChestplateRenderer();

                return this.renderer;
            }
        });
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
