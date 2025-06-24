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

public class BambooHatModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("bamboo_hat"), "main");
    public static final ResourceLocation TEXTURE = Minejago.modLoc("textures/entity/bamboo_hat.png");
    public static final ResourceLocation HOLIDAY_TEXTURE = Minejago.modLoc("textures/entity/bamboo_hat_holiday.png");

    private final ModelPart hat;

    public BambooHatModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.hat = root.getChild("hat");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(24, 0).addBox(-0.5F, -5.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.75F, 0.0F));

        PartDefinition back_r1 = hat.addOrReplaceChild("back_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -4.0F, 12.0F, 12.0F, 0.001F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0908F, 3.1416F, 0.0F));

        PartDefinition right_r1 = hat.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -4.0F, 12.0F, 12.0F, 0.001F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0908F, -1.5708F, 0.0F));

        PartDefinition left_r1 = hat.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -4.0F, 12.0F, 12.0F, 0.001F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0908F, 1.5708F, 0.0F));

        PartDefinition front_r1 = hat.addOrReplaceChild("front_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -4.0F, 12.0F, 12.0F, 0.001F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0908F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        this.hat.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
