package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.DragonExtremeGiRenderer;
import java.util.function.Consumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

public class DragonExtremeGiArmorItem extends TraineeGiArmorItem {
    public DragonExtremeGiArmorItem(Type type, Properties pProperties) {
        super(type, pProperties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private DragonExtremeGiRenderer renderer;

            @Override
            public @Nullable <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if (this.renderer == null)
                    this.renderer = new DragonExtremeGiRenderer();

                return this.renderer;
            }
        });
    }
}
