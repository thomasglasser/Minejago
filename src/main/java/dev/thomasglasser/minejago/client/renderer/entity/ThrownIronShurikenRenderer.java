package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.ThrownIronShurikenModel;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownIronShuriken;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ThrownIronShurikenRenderer extends EntityRenderer<ThrownIronShuriken> {
    public static final ResourceLocation TEXTURE_LOCATION = Minejago.modLoc("textures/entity/item/iron_shuriken.png");
    private final ThrownIronShurikenModel model;

    public ThrownIronShurikenRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ThrownIronShurikenModel(context.bakeLayer(ThrownIronShurikenModel.LAYER_LOCATION));
    }

    public void render(ThrownIronShuriken pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
        if (!pEntity.hasDealtDamage())
            pMatrixStack.mulPose(Axis.XP.rotationDegrees(pEntity.getXRot() + (float) (90.0F * (Math.random() * 4))));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.model.renderType(this.getTextureLocation(pEntity)), false, pEntity.isFoil());
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(ThrownIronShuriken pEntity) {
        return TEXTURE_LOCATION;
    }
}
