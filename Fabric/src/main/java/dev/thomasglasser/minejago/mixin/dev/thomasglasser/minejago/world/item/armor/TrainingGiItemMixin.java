package dev.thomasglasser.minejago.mixin.dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.armor.SkeletalArmorRenderer;
import dev.thomasglasser.minejago.client.renderer.armor.TrainingGiRenderer;
import dev.thomasglasser.minejago.world.item.IFabricGeoItem;
import dev.thomasglasser.minejago.world.item.armor.TrainingGiItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(TrainingGiItem.class)
public abstract class TrainingGiItemMixin implements IFabricGeoItem
{
    Supplier<Object> renderProvider = GeoItem.makeRenderer((TrainingGiItem)(Object)this);

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GeoArmorRenderer<?> renderer = new TrainingGiRenderer();

            @Override
            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
                return renderer;
            }
        });
    }
}
