package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.client.model.FreezingIceModel;
import dev.thomasglasser.minejago.world.effect.FrozenMobEffect;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class FreezingIceLayer<E extends LivingEntity> extends RenderLayer<E, EntityModel<E>> {
    private final FreezingIceModel model;

    public FreezingIceLayer(RenderLayerParent<E, EntityModel<E>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new FreezingIceModel(modelSet.bakeLayer(FreezingIceModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, E entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        int width = Mth.ceil(entity.getBbWidth());
        int height = Mth.ceil(entity.getBbHeight());
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucentCull(FreezingIceModel.TEXTURE));
        if (TommyLibServices.ENTITY.getPersistentData(entity).getBoolean(FrozenMobEffect.TAG_FROZEN)) {
            poseStack.translate(-width, 0, -width);
            renderColumn(poseStack, vertexConsumer, packedLight, height);
            for (float z = 0; z < width * 2 + 1; z++) {
                for (float x = 0; x < width * 2; x++) {
                    poseStack.translate(1, 0, 0);
                    renderColumn(poseStack, vertexConsumer, packedLight, height);
                }
                if (z + 1 < width * 2 + 1) {
                    poseStack.translate(-(width * 2), 0, 1);
                    renderColumn(poseStack, vertexConsumer, packedLight, height);
                }
            }
        }
    }

    private void renderColumn(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int height) {
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.pushPose();
        for (int y = 0; y < height; y++) {
            poseStack.translate(0.0D, -1, 0.0D);
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
        poseStack.popPose();
    }
}
