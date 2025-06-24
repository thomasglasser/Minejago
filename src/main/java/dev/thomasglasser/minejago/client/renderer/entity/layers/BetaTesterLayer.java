package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.client.model.BambooHatModel;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BetaTesterLayer<S extends AbstractClientPlayer> extends RenderLayer<S, PlayerModel<S>> {
    private final boolean holidayTextures;
    private final BambooHatModel bambooHatModel;

    public BetaTesterLayer(RenderLayerParent<S, PlayerModel<S>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.holidayTextures = ClientUtils.isHoliday();

        this.bambooHatModel = new BambooHatModel(modelSet.bakeLayer(BambooHatModel.LAYER_LOCATION));
    }

    @Override
    protected ResourceLocation getTextureLocation(S player) {
        return switch (MinejagoClientUtils.betaChoice(player)) {
            case BAMBOO_HAT -> holidayTextures ? BambooHatModel.HOLIDAY_TEXTURE : BambooHatModel.TEXTURE;
        };
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (MinejagoClientUtils.shouldRenderBetaTesterLayer(player)) {
            poseStack.pushPose();
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(player)));
            getParentModel().getHead().translateAndRotate(poseStack);
            switch (MinejagoClientUtils.betaChoice(player)) {
                case BAMBOO_HAT -> bambooHatModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            }
            poseStack.popPose();
        }
    }
}
