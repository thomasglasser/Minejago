package dev.thomasglasser.minejago.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SpinjitzuModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("spinjitzu"), "main");

    public final ModelPart body;
    private final ModelPart bottom;
    private final ModelPart midBottom;
    private final ModelPart mid;
    private final ModelPart midTop;
    private final ModelPart top;

    public SpinjitzuModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.body = root.getChild("body");
        this.bottom = body.getChild("bottom");
        this.midBottom = bottom.getChild("mid_bottom");
        this.mid = midBottom.getChild("mid");
        this.midTop = mid.getChild("mid_top");
        this.top = midTop.getChild("top");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(39, 112).addBox(-4.5F, -7.0F, -4.5F, 9.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mid_bottom = bottom.addOrReplaceChild("mid_bottom", CubeListBuilder.create().texOffs(0, 96).addBox(-6.0F, -13.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mid = mid_bottom.addOrReplaceChild("mid", CubeListBuilder.create().texOffs(0, 70).addBox(-9.0F, -21.0F, -9.0F, 18.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mid_top = mid.addOrReplaceChild("mid_top", CubeListBuilder.create().texOffs(0, 38).addBox(-12.0F, -29.0F, -12.0F, 24.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition top = mid_top.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -37.0F, -15.0F, 30.0F, 8.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        body.render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks * (float) Math.PI * -0.1F;
        this.bottom.x = Mth.cos(f) * 1.0F * 0.6F;
        this.bottom.z = Mth.sin(f) * 1.0F * 0.6F;
        this.midBottom.x = Mth.sin(f) * 0.5F * 0.8F;
        this.midBottom.z = Mth.cos(f) * 0.8F;
        this.mid.x = Mth.sin(f) * 0.5F * 0.8F;
        this.mid.z = Mth.cos(f) * 0.8F;
        this.midTop.x = Mth.sin(f) * 0.5F * 0.8F;
        this.midTop.z = Mth.cos(f) * 0.8F;
        this.top.x = Mth.cos(f) * -0.25F * 1.0F;
        this.top.z = Mth.sin(f) * -0.25F * 1.0F;
    }
}
