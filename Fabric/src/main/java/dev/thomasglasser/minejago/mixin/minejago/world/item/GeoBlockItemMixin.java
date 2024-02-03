package dev.thomasglasser.minejago.mixin.minejago.world.item;

import dev.thomasglasser.tommylib.api.world.item.FabricGeoItem;
import dev.thomasglasser.tommylib.api.world.item.GeoBlockItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(GeoBlockItem.class)
public abstract class GeoBlockItemMixin implements FabricGeoItem
{
    private final GeoBlockItem INSTANCE = ((GeoBlockItem) (Object)this);

    Supplier<Object> renderProvider = GeoItem.makeRenderer((GeoBlockItem)(Object)this);

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = INSTANCE.getBEWLR();

                return this.renderer;
            }
        });
    }
}
