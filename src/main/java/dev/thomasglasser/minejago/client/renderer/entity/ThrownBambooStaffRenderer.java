package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import dev.thomasglasser.minejago.MinejagoMod;
import dev.thomasglasser.minejago.client.model.ThrownBambooStaffModel;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownBambooStaff;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ThrownBambooStaffRenderer extends EntityRenderer<ThrownBambooStaff>
{
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(MinejagoMod.MODID, "textures/entity/bamboo_staff.png");
    private final ThrownBambooStaffModel model;

    public ThrownBambooStaffRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ThrownBambooStaffModel(context.bakeLayer(ThrownBambooStaffModel.LAYER_LOCATION));
    }

    public void render(ThrownBambooStaff pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.model.renderType(this.getTextureLocation(pEntity)), false, pEntity.isFoil());
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(ThrownBambooStaff pEntity) {
        return TEXTURE_LOCATION;
    }
}
