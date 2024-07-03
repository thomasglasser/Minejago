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

public class ScytheModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("scythe"), "in_hand");
    private final ModelPart body;

    public ScytheModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();

        meshdefinition.getRoot().addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -25.0F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 5).addBox(-5.5F, -28.0F, -1.0F, 7.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 10).addBox(-6.5F, -27.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-7.5F, -29.0F, 0.0F, 9.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, int p_350308_) {
        body.render(p_103111_, p_103112_, p_103113_, p_103114_, p_350308_);
    }
}
