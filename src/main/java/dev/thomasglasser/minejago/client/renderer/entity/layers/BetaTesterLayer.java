package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.BambooHatModel;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Calendar;

public class BetaTesterLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation BAMBOO_HAT_TEXTURE = new ResourceLocation(Minejago.MOD_ID, "textures/entity/player/bamboo_hat.png");
    private static final ResourceLocation HOLIDAY_HAT_TEXTURE = new ResourceLocation(Minejago.MOD_ID, "textures/entity/player/holiday_hat.png");

    private final BambooHatModel bambooHatModel;

    public BetaTesterLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer, EntityModelSet modelSet) {
        super(renderer);

        this.bambooHatModel = new BambooHatModel(modelSet.bakeLayer(BambooHatModel.LAYER_LOCATION));
    }

    @Override
    protected ResourceLocation getTextureLocation(AbstractClientPlayer entity) {
        return switch (LayersConfig.BETA_CHOICE.get())
        {

            case BAMBOO_HAT ->
            {
                if (Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER)
                    yield HOLIDAY_HAT_TEXTURE;
                else
                    yield BAMBOO_HAT_TEXTURE;
            }

            default -> new ResourceLocation("");
        };
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(player)));

        bambooHatModel.body.copyFrom(getParentModel().getHead());

        float f = Mth.lerp(partialTick, player.yRotO, player.getYRot()) - Mth.lerp(partialTick, player.yBodyRotO, player.yBodyRot);
        float f1 = Mth.lerp(partialTick, player.xRotO, player.getXRot());
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        poseStack.mulPose(Axis.XP.rotationDegrees(f1));
        poseStack.translate(0.0D, -0.26D, 0.0D);
        poseStack.mulPose(Axis.XP.rotationDegrees(-f1));
        poseStack.mulPose(Axis.YP.rotationDegrees(-f));
        poseStack.scale(1.3333334F, 1.3333334F, 1.3333334F);
        if (MinejagoClientUtils.renderBetaLayer(player))
        {
            switch (MinejagoClientUtils.betaChoice(player))
            {
                case BAMBOO_HAT -> bambooHatModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        poseStack.popPose();
    }
}
