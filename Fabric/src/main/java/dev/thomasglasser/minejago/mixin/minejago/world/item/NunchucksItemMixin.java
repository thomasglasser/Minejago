package dev.thomasglasser.minejago.mixin.minejago.world.item;

import com.google.common.collect.Multimap;
import dev.thomasglasser.minejago.client.renderer.item.WoodenNunchucksRenderer;
import dev.thomasglasser.minejago.world.item.FabricGeoItem;
import dev.thomasglasser.minejago.world.item.NunchucksItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(NunchucksItem.class)
public abstract class NunchucksItemMixin implements FabricGeoItem, FabricItem
{
    Supplier<Object> renderProvider = GeoItem.makeRenderer((NunchucksItem)(Object)this);

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private WoodenNunchucksRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new WoodenNunchucksRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        return ((NunchucksItem)(Object)this).getAttributeModifiers(slot, stack);
    }
}
