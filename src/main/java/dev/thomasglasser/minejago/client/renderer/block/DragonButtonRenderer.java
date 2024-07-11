package dev.thomasglasser.minejago.client.renderer.block;

import dev.thomasglasser.minejago.world.level.block.DragonButtonBlock;
import dev.thomasglasser.minejago.world.level.block.entity.DragonButtonBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DragonButtonRenderer extends GeoBlockRenderer<DragonButtonBlockEntity> {
    public DragonButtonRenderer(ResourceLocation location) {
        super(new DefaultedBlockGeoModel<>(location) {
            ResourceLocation top;
            ResourceLocation bottom;

            @Override
            public ResourceLocation buildFormattedModelPath(ResourceLocation basePath) {
                top = basePath.withPath("geo/" + subtype() + "/" + basePath.getPath() + "_top.geo.json");
                bottom = basePath.withPath("geo/" + subtype() + "/" + basePath.getPath() + "_bottom.geo.json");
                return super.buildFormattedModelPath(basePath);
            }

            @Override
            public ResourceLocation getModelResource(DragonButtonBlockEntity animatable) {
                return animatable.getBlockState().getValue(DragonButtonBlock.PART) == DragonButtonBlock.Part.TOP ? top : bottom;
            }
        });
    }
}
