package dev.thomasglasser.minejago.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.client.model.DragonHeadModel;
import dev.thomasglasser.minejago.world.entity.GoldenWeaponHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DragonHeadRenderer extends GeoEntityRenderer<GoldenWeaponHolder> {
    public DragonHeadRenderer(EntityRendererProvider.Context renderManager, ResourceLocation assetSubpath) {
        super(renderManager, new DragonHeadModel(assetSubpath));
    }

    @Override
    public void defaultRender(PoseStack poseStack, GoldenWeaponHolder animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        model.getBone("weapon").ifPresent(geoBone -> geoBone.setHidden(!animatable.hasGoldenWeapon()));
        super.defaultRender(poseStack, animatable, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
    }

    @Override
    public void preRender(PoseStack poseStack, GoldenWeaponHolder animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - animatable.getDirection().getClockWise().toYRot()));
    }
}
