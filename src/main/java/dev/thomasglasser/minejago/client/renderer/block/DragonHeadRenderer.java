package dev.thomasglasser.minejago.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.client.model.DragonHeadModel;
import dev.thomasglasser.minejago.world.level.block.entity.DragonHeadBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class DragonHeadRenderer extends GeoBlockRenderer<DragonHeadBlockEntity> {
    public DragonHeadRenderer() {
        super(new DragonHeadModel());
    }

    @Override
    public void defaultRender(PoseStack poseStack, DragonHeadBlockEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float partialTick, int packedLight) {
        model.getBone("scythe").ifPresent(geoBone -> geoBone.setHidden(!animatable.hasScythe));
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, partialTick, packedLight);
    }
}
