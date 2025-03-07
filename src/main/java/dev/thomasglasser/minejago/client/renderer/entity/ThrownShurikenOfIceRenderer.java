package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.client.model.ShurikenModel;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownShurikenOfIce;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ThrownShurikenOfIceRenderer extends EntityRenderer<ThrownShurikenOfIce> {
    private final ShurikenModel model;

    public ThrownShurikenOfIceRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ShurikenModel(context.bakeLayer(ShurikenModel.LAYER_LOCATION));
    }

    @Override
    public void render(ThrownShurikenOfIce entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        poseStack.translate(0, -1.25, -0.25);
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferSource, this.model.renderType(this.getTextureLocation(entity)), false, entity.isFoil());
        this.model.rotate((entity.tickCount / 2F) % 360);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownShurikenOfIce thrownShurikenOfIce) {
        return ShurikenModel.TEXTURE;
    }
}
