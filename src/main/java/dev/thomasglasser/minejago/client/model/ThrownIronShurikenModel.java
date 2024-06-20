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

public class ThrownIronShurikenModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("iron_shuriken"), "main");
	private final ModelPart body;

	public ThrownIronShurikenModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition improved = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(11, 5).addBox(-0.5F, 1.5F, -0.5F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 13).addBox(-0.5F, -6.5F, -0.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 10).addBox(-0.5F, -3.5F, -2.5F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(9, 0).addBox(-0.5F, -1.5F, -4.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, 0.5F, -7.5F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(22, 0).addBox(-0.5F, -1.5F, 1.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, 3.5F, 4.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(-0.5F, 4.5F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(-0.5F, 2.5F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(-0.5F, 0.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 0).addBox(-0.5F, -1.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 0).addBox(-0.5F, -1.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(-0.5F, -4.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 0).addBox(-0.5F, -2.5F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 0).addBox(-0.5F, -0.5F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 0).addBox(-0.5F, 1.5F, -9.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 0).addBox(-0.5F, 3.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 5).addBox(-0.5F, -7.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-0.5F, -8.5F, 1.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-0.5F, -0.5F, 3.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 10).addBox(-0.5F, 1.5F, -8.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, int p_350308_)
	{
		this.body.render(p_103111_, p_103112_, p_103113_, p_103114_, p_350308_);
	}
}