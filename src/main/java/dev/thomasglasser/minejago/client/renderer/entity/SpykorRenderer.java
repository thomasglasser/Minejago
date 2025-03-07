package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.Spykor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class SpykorRenderer<T extends Spykor> extends GeoEntityRenderer<T> {
    public SpykorRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(Minejago.modLoc("skulkin/spykor")));
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void defaultRender(PoseStack poseStack, T animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
        if (animatable.isResting() || animatable.isFalling()) {
            poseStack.pushPose();
            Vec3 restingPos = animatable.getRestingPos().add(0, 2, 0);
            Vec3 spykorPos = animatable.getPosition(partialTick).add(0, 0, 0);
            Vec3 fromRestingToSpykor = new Vec3(restingPos.x - spykorPos.x, restingPos.y - spykorPos.y, restingPos.z - spykorPos.z); //relative to projectile
            Vec3 thickness = new Vec3(getSegmentThickness(spykorPos, restingPos).toVector3f());
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath("neoforge", "textures/white.png")));
            PoseStack.Pose pose = poseStack.last();
            vertex(vertexConsumer, pose, (float) -thickness.x, (float) -thickness.y, (float) -thickness.z, 0f, 1f);
            vertex(vertexConsumer, pose, (float) (fromRestingToSpykor.x - thickness.x), (float) (fromRestingToSpykor.y - thickness.y), (float) (fromRestingToSpykor.z - thickness.z), 1f, 1f);
            vertex(vertexConsumer, pose, (float) (fromRestingToSpykor.x + thickness.x), (float) (fromRestingToSpykor.y + thickness.y), (float) (fromRestingToSpykor.z + thickness.z), 1f, 0f);
            vertex(vertexConsumer, pose, (float) thickness.x, (float) +thickness.y, (float) thickness.z, 0f, 0f);
            poseStack.popPose();
        }
    }

    private static void vertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, float x, float y, float z, float i, float j) {
        vertexConsumer.addVertex(pose, x, y, z).setColor(-1).setUv(i, j).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0.0F, 1.0F, 0.0F);
    }

    private Vec3 getSegmentThickness(Vec3 p1, Vec3 p2) { // p1 projectile; p2 hand
        Vec3 fromP1P2 = new Vec3(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z); //relative to p1
        Vec3 fromP1P2OnXZ = new Vec3(fromP1P2.x, 0, fromP1P2.z);
        Vec3 watcherEyePos = Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition();
        Vec3 fromP1ToPOV = new Vec3(watcherEyePos.x - p1.x, watcherEyePos.y - p1.y, watcherEyePos.z - p1.z); //fromPoint1ToPointOfView

        double XZGraphLineConst = fromP1P2OnXZ.z / fromP1P2OnXZ.x; // tan(alpha), alpha is the smallest angle between the fromP1P2OnXZ's support line and OX
        double XZGraphPerpendicularConst = -1 * fromP1P2OnXZ.x / fromP1P2OnXZ.z; // - ctan(alpha), the perpendicular from POV to fromP1P2OnXZ's support line.
        double offsetParallelLine = fromP1ToPOV.z - fromP1ToPOV.x * XZGraphPerpendicularConst;
        double projectionX = offsetParallelLine / (XZGraphLineConst * XZGraphLineConst + 1);
        double projectionZ = projectionX * XZGraphLineConst;

        Vec3 projXZ = new Vec3(projectionX, 0, projectionZ);
        double projXZlength = projXZ.length(); // this SHOULD be negative, we need it to determine the projY
        if (!((fromP1P2OnXZ.x >= 0) == (projXZ.x >= 0))) {
            projXZlength = projXZlength * (-1); // here we are making it negative if needed 👆
        }

        double projectionY = fromP1P2.y * projXZlength / fromP1P2OnXZ.length();

        Vec3 fromPOVToRope = new Vec3(projectionX - fromP1ToPOV.x, projectionY - fromP1ToPOV.y, projectionZ - fromP1ToPOV.z);

        Vec3 segmentThinkness = fromPOVToRope.cross(fromP1ToPOV);
        segmentThinkness = segmentThinkness.scale(1 / segmentThinkness.length());
        segmentThinkness = segmentThinkness.scale(0.02d); //0.02 is the thickness
        return segmentThinkness;
    }
}
