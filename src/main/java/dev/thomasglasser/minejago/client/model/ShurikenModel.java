package dev.thomasglasser.minejago.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.client.renderer.entity.ThrownSwordRenderer;
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
import net.minecraft.resources.ResourceLocation;

public class ShurikenModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("shuriken"), "main");
    public static final ResourceLocation TEXTURE = ThrownSwordRenderer.TEXTURE.apply(MinejagoItems.SHURIKEN_OF_ICE.getId());

    private final ModelPart shuriken;

    public ShurikenModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.shuriken = root.getChild("shuriken");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition shuriken = partdefinition.addOrReplaceChild("shuriken", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 1.75F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 20.25F, 0.0F));

        PartDefinition cube_r1 = shuriken.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.9F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.9F, -1.25F, 0.0F, 0.0F, 0.0F, -2.0944F));

        PartDefinition cube_r2 = shuriken.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.3F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.65F, 0.0F, 0.0F, 0.0F, 2.0944F));

        PartDefinition cube_r3 = shuriken.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 4).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.55F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r4 = shuriken.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 4).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1122F, 2.6993F, 0.0F, 0.0F, 0.0F, 1.0472F));

        PartDefinition cube_r5 = shuriken.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 4).addBox(-1.5F, -1.9634F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5778F, 1.8134F, 0.0F, 0.0F, 0.0F, -1.0472F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    public void rotate(float f) {
        shuriken.zRot = f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        shuriken.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
