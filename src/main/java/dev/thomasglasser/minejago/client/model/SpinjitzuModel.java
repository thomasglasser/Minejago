package dev.thomasglasser.minejago.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SpinjitzuModel<T extends Entity> extends EntityModel<T> {
    public static final ResourceLocation BASE_TEXTURE = Minejago.modLoc("textures/entity/player/spinjitzu.png");
    public static final ResourceLocation SWIRL_TEXTURE = Minejago.modLoc("textures/entity/player/spinjitzu_swirl.png");

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("spinjitzu"), "main");

    private final ModelPart body;
    private final ModelPart inner;
    private final ModelPart innerTop;
    private final ModelPart innerMidTop;
    private final ModelPart innerMid;
    private final ModelPart innerMidBottom;
    private final ModelPart innerBottom;
    private final ModelPart outer;
    private final ModelPart outerTop;
    private final ModelPart outerMidTop;
    private final ModelPart outerMid;
    private final ModelPart outerMidBottom;
    private final ModelPart outerBottom;

    public SpinjitzuModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.body = root.getChild("body");
        this.inner = body.getChild("inner");
        this.innerTop = inner.getChild("inner_top");
        this.innerMidTop = inner.getChild("inner_mid_top");
        this.innerMid = inner.getChild("inner_mid");
        this.innerMidBottom = inner.getChild("inner_mid_bottom");
        this.innerBottom = inner.getChild("inner_bottom");
        this.outer = body.getChild("outer");
        this.outerTop = outer.getChild("outer_top");
        this.outerMidTop = outer.getChild("outer_mid_top");
        this.outerMid = outer.getChild("outer_mid");
        this.outerMidBottom = outer.getChild("outer_mid_bottom");
        this.outerBottom = outer.getChild("outer_bottom");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition inner = body.addOrReplaceChild("inner", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        inner.addOrReplaceChild("inner_top", CubeListBuilder.create().texOffs(0, 38).addBox(-12.0F, -24.0F, -12.0F, 24.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

        inner.addOrReplaceChild("inner_mid_top", CubeListBuilder.create().texOffs(78, 52).addBox(-9.0F, -16.0F, -9.0F, 18.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

        inner.addOrReplaceChild("inner_mid", CubeListBuilder.create().texOffs(90, 0).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

        inner.addOrReplaceChild("inner_mid_bottom", CubeListBuilder.create().texOffs(75, 110).addBox(-4.5F, -6.0F, -4.5F, 9.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

        inner.addOrReplaceChild("inner_bottom", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -7.0F, -3.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 1.0F));

        PartDefinition outer = body.addOrReplaceChild("outer", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        outer.addOrReplaceChild("outer_top", CubeListBuilder.create().texOffs(0, 66).addBox(-15.0F, -24.0F, -15.0F, 30.0F, 8.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

        outer.addOrReplaceChild("outer_mid_top", CubeListBuilder.create().texOffs(0, 8).addBox(-12.0F, -16.0F, -12.0F, 24.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

        outer.addOrReplaceChild("outer_mid", CubeListBuilder.create().texOffs(0, 142).addBox(-9.0F, -8.0F, -9.0F, 18.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

        outer.addOrReplaceChild("outer_mid_bottom", CubeListBuilder.create().texOffs(0, 180).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

        outer.addOrReplaceChild("outer_bottom", CubeListBuilder.create().texOffs(0, 215).addBox(-4.5F, -7.0F, -4.5F, 9.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public void render(PoseStack poseStack, MultiBufferSource source, int tickCount, float partialTick, int color) {
        float f = (float) tickCount + partialTick;
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        inner.render(poseStack, source.getBuffer(RenderType.entityTranslucent(BASE_TEXTURE)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0xFF000000 | color);
        outer.render(poseStack, source.getBuffer(RenderType.breezeWind(SWIRL_TEXTURE, xOffset(f) % 1.0F, 0.0F)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0xFF000000 | color);
        poseStack.popPose();
    }

    private static float xOffset(float tickCount) {
        return tickCount * 0.02F;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks * (float) Math.PI * -0.1F;
        this.outerBottom.x = Mth.cos(f) * 1.0F * 0.6F;
        this.outerBottom.z = Mth.sin(f) * 1.0F * 0.6F;
        this.innerBottom.x = Mth.cos(f) * 1.0F * 0.6F;
        this.innerBottom.z = Mth.sin(f) * 1.0F * 0.6F;
        this.outerMidBottom.x = Mth.sin(f) * 0.5F * 0.8F;
        this.outerMidBottom.z = Mth.cos(f) * 0.8F;
        this.innerMidBottom.x = Mth.sin(f) * 0.5F * 0.8F;
        this.innerMidBottom.z = Mth.cos(f) * 0.8F;
        this.outerMid.x = Mth.sin(f) * 0.5F * 0.8F;
        this.outerMid.z = Mth.cos(f) * 0.8F;
        this.innerMid.x = Mth.sin(f) * 0.5F * 0.8F;
        this.innerMid.z = Mth.cos(f) * 0.8F;
        this.outerMidTop.x = Mth.sin(f) * 0.5F * 0.8F;
        this.outerMidTop.z = Mth.cos(f) * 0.8F;
        this.innerMidTop.x = Mth.sin(f) * 0.5F * 0.8F;
        this.innerMidTop.z = Mth.cos(f) * 0.8F;
        this.outerTop.x = Mth.cos(f) * -0.25F * 1.0F;
        this.outerTop.z = Mth.sin(f) * -0.25F * 1.0F;
        this.innerTop.x = Mth.cos(f) * -0.25F * 1.0F;
        this.innerTop.z = Mth.sin(f) * -0.25F * 1.0F;
    }

    public ModelPart getBody() {
        return body;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        body.render(poseStack, vertexConsumer, i, i1, i2);
    }
}
