package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.TraineeGiRenderer;
import dev.thomasglasser.minejago.world.item.equipment.MinejagoArmorMaterials;
import java.util.function.Consumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentModel;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TraineeGiItem extends PoweredArmorItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TraineeGiItem(ArmorType type, Properties pProperties) {
        super(MinejagoArmorMaterials.TRAINEE_GI, type, pProperties);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private TraineeGiRenderer renderer;

            @Override
            public @Nullable <E extends LivingEntity, S extends HumanoidRenderState> HumanoidModel<?> getGeoArmorRenderer(@Nullable E livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, EquipmentModel.LayerType type, HumanoidModel<S> original) {
                if (this.renderer == null)
                    this.renderer = new TraineeGiRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
