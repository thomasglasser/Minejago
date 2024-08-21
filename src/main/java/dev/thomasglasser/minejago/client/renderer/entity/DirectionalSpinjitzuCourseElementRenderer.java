package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DirectionalSpinjitzuCourseElementRenderer<T extends AbstractSpinjitzuCourseElement<T>> extends GeoEntityRenderer<T> {
    public DirectionalSpinjitzuCourseElementRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - animatable.getDirection().getClockWise().toYRot()));
    }
}
