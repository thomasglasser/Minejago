package dev.thomasglasser.minejago.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SpearModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Minejago.MOD_ID, "spear"), "in_hand");
	private final ModelPart body;

	public SpearModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();

		meshdefinition.getRoot().addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 2.0F, -0.75F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(4, 0).addBox(-1.0F, -4.0F, -0.75F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(5, 7).addBox(0.0F, -6.0F, -0.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(14, 11).addBox(-1.0F, -5.0F, -0.25F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(4, 10).addBox(-1.0F, 2.0F, -0.25F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(12, 8).addBox(-2.0F, 0.0F, -0.25F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -3.0F, 0.25F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}