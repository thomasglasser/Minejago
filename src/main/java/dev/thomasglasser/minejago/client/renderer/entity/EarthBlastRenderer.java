package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.entity.state.EarthBlastRenderState;
import dev.thomasglasser.minejago.world.entity.projectile.EarthBlast;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class EarthBlastRenderer extends EntityRenderer<EarthBlast, EarthBlastRenderState> {
    private static final List<ResourceLocation> TEXTURE_LOCATIONS = List.of(
            Minejago.modLoc("textures/particle/rock_0.png"),
            Minejago.modLoc("textures/particle/rock_1.png"),
            Minejago.modLoc("textures/particle/rock_2.png"),
            Minejago.modLoc("textures/particle/rock_3.png"));

    public EarthBlastRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(EarthBlastRenderState renderState, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(2.0F, 2.0F, 2.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(renderState)));
        vertex(vertexConsumer, pose, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, pose, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, pose, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, pose, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(renderState, poseStack, buffer, packedLight);
    }

    @Override
    public EarthBlastRenderState createRenderState() {
        return new EarthBlastRenderState();
    }

    @Override
    public void extractRenderState(EarthBlast p_362104_, EarthBlastRenderState p_361028_, float p_362204_) {
        super.extractRenderState(p_362104_, p_361028_, p_362204_);
        p_361028_.variant = p_362104_.getVariant();
    }

    private static void vertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, int i, float f, int j, int k, int l) {
        vertexConsumer.addVertex(pose, f - 0.5F, (float) j - 0.25F, 0.0F).setColor(255, 255, 255, 255).setUv((float) k, (float) l).setOverlay(OverlayTexture.NO_OVERLAY).setLight(i).setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(EarthBlastRenderState renderState) {
        return TEXTURE_LOCATIONS.get(renderState.variant);
    }
}
