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

public class OgDevTeamBeardModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("og_dev_team_beard"), "main");
    public final ModelPart body;

    public OgDevTeamBeardModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.body = root.getChild("beard");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition beard = partdefinition.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(13, 4).addBox(-1.0F, 4.0F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 2).addBox(-3.0F, 5.0F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 0).addBox(1.0F, 5.0F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 11).addBox(-1.0F, 6.0F, -5.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 0).addBox(-3.0F, 4.0F, -4.5F, 6.0F, 11.0F, 0.001F, new CubeDeformation(0.0F))
                .texOffs(11, 11).addBox(-3.0F, 6.0F, -5.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 11).addBox(2.0F, 6.0F, -5.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, int p_350308_) {
        this.body.render(p_103111_, p_103112_, p_103113_, p_103114_, p_350308_);
    }
}
