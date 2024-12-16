package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.client.model.LegacyDevTeamBeardModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class LegacyDevTeamLayer<S extends AbstractClientPlayer> extends RenderLayer<S, PlayerModel<S>> {
    private final LegacyDevTeamBeardModel model;

    public LegacyDevTeamLayer(RenderLayerParent<S, PlayerModel<S>> renderer, EntityModelSet modelSet) {
        super(renderer);

        this.model = new LegacyDevTeamBeardModel(modelSet.bakeLayer(LegacyDevTeamBeardModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, S livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(LegacyDevTeamBeardModel.TEXTURE));
        poseStack.translate(0, -0.055, 0);
        if (MinejagoClientUtils.renderLegacyDevLayer(livingEntity))
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
    }
}
