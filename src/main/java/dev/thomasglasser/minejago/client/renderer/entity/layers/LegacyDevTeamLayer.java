package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.client.model.LegacyDevTeamBeardModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class LegacyDevTeamLayer<S extends PlayerRenderState> extends RenderLayer<S, PlayerModel> {
    private final LegacyDevTeamBeardModel model;

    public LegacyDevTeamLayer(RenderLayerParent<S, PlayerModel> renderer, EntityModelSet modelSet) {
        super(renderer);

        this.model = new LegacyDevTeamBeardModel(modelSet.bakeLayer(LegacyDevTeamBeardModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, S renderState, float p_117353_, float p_117354_) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(LegacyDevTeamBeardModel.TEXTURE));
        poseStack.translate(0, -0.055, 0);
        Boolean renderLegacyDev = renderState.getRenderData(MinejagoClientUtils.RENDER_LEGACY_DEV);
        if (renderLegacyDev != null && renderLegacyDev)
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
    }
}
