package dev.thomasglasser.minejago.client.model.armor;

// Made with Blockbench 4.5.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SkeletalChestplateModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Minejago.MOD_ID, "skeletal_chestplate"), "main");
	private final ModelPart Head;
	private final ModelPart Body;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;

	public SkeletalChestplateModel(ModelPart root) {
		this.Head = root.getChild("Head");
		this.Body = root.getChild("Body");
		this.RightArm = root.getChild("RightArm");
		this.LeftArm = root.getChild("LeftArm");
		this.RightLeg = root.getChild("RightLeg");
		this.LeftLeg = root.getChild("LeftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1047F, 0.0873F, 0.0F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F))
				.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 16).addBox(-4.0F, -1.0F, -7.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(28, 16).addBox(-4.0F, -1.0F, -7.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 32).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.9847F, -0.6192F, -3.0F, 0.0F, 3.1416F, -0.2269F));

		PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 20).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 20).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.8527F, -3.1961F, 0.0F, 0.0F, 0.0F, 0.6196F));

		PartDefinition cube_r3 = Body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 24).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 36).addBox(-4.0F, -1.0F, 5.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 24).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 36).addBox(-4.0F, -1.0F, 5.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.9065F, -0.5087F, -3.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition cube_r4 = Body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(36, 0).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(36, 0).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4564F, -3.3584F, 0.0F, 0.0F, 3.1416F, -0.6196F));

		PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.2094F, 0.0F, 0.0F));

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.192F, 0.0F, 0.0349F));

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, -0.1745F, 0.0F, -0.0349F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}