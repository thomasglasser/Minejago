package dev.thomasglasser.minejago.mixin.dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.client.renderer.armor.SkeletalArmorRenderer;
import dev.thomasglasser.minejago.client.renderer.item.WoodenNunchucksRenderer;
import dev.thomasglasser.minejago.world.item.IFabricGeoItem;
import dev.thomasglasser.minejago.world.item.WoodenNunchucksItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(WoodenNunchucksItem.class)
public abstract class WoodenNunchucksItemMixin implements IFabricGeoItem
{
    Supplier<Object> renderProvider = GeoItem.makeRenderer((WoodenNunchucksItem)(Object)this);

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GeoItemRenderer<?> renderer = new WoodenNunchucksRenderer();

            public GeoItemRenderer<?> getRenderer() {
                return renderer;
            }
        });
    }
}
