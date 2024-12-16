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
import net.minecraft.resources.ResourceLocation;

public class LegacyDevTeamBeardModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("legacy_dev_team_beard"), "main");
    public static final ResourceLocation TEXTURE = Minejago.modLoc("textures/entity/player/legacy_dev_team_beard.png");

    private final ModelPart beard;

    public LegacyDevTeamBeardModel(ModelPart root) {
        super(RenderType::entityCutout);
        beard = root.getChild("beard");
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
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        beard.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
