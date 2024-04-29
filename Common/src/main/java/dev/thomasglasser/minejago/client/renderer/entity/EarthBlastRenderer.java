package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.projectile.EarthBlast;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class EarthBlastRenderer extends EntityRenderer<EarthBlast>
{
	private static final List<ResourceLocation> TEXTURE_LOCATIONS = List.of(
			Minejago.modLoc("textures/particle/rock_0.png"),
			Minejago.modLoc("textures/particle/rock_1.png"),
			Minejago.modLoc("textures/particle/rock_2.png"),
			Minejago.modLoc("textures/particle/rock_3.png")
	);

	public EarthBlastRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	public void render(EarthBlast entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		poseStack.pushPose();
		poseStack.scale(2.0F, 2.0F, 2.0F);
		poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		PoseStack.Pose pose = poseStack.last();
		VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
		vertex(vertexConsumer, pose, packedLight, 0.0F, 0, 0, 1);
		vertex(vertexConsumer, pose, packedLight, 1.0F, 0, 1, 1);
		vertex(vertexConsumer, pose, packedLight, 1.0F, 1, 1, 0);
		vertex(vertexConsumer, pose, packedLight, 0.0F, 1, 0, 0);
		poseStack.popPose();
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}

	private static void vertex(VertexConsumer vertexConsumer, PoseStack.Pose pose, int i, float f, int j, int k, int l) {
		vertexConsumer.vertex(pose, f - 0.5F, (float)j - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)k, (float)l).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(i).normal(pose, 0.0F, 1.0F, 0.0F).endVertex();
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getTextureLocation(EarthBlast entity) {
		return TEXTURE_LOCATIONS.get(entity.getVariant());
	}
}