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
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class ThrownBoneKnifeModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("bone_knife"), "main");
	private final ModelPart body;

	public ThrownBoneKnifeModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 8).addBox(3.0F, -5.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(7, 5).addBox(3.0F, -12.0F, 6.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(10, 0).addBox(3.0F, -14.0F, 9.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(13, 9).addBox(3.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(12, 12).addBox(3.0F, -5.0F, 4.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(8, 11).addBox(3.0F, -13.0F, 7.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(6, 0).addBox(3.0F, -12.0F, 9.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(12, 5).addBox(3.0F, -9.0F, 6.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(4, 14).addBox(3.0F, -4.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 14).addBox(3.0F, -9.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(3.0F, -11.0F, 5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(3.0F, -9.0F, 2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 17).addBox(3.0F, -6.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 12).addBox(3.0F, -5.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 16).addBox(3.0F, -6.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(15, 15).addBox(3.0F, -8.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(15, 0).addBox(3.0F, -14.0F, 12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(8, 14).addBox(3.0F, -11.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 5).addBox(3.0F, -10.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(11, 15).addBox(3.0F, -10.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 16.0F, 3.75F, 0.0F, 1.5708F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, int p_350308_)
	{
		this.body.render(p_103111_, p_103112_, p_103113_, p_103114_, p_350308_);
	}
}