package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.client.model.PilotsSnapshotTesterHatModel;
import java.util.Calendar;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SnapshotTesterLayer<S extends PlayerRenderState> extends RenderLayer<S, PlayerModel> {
    private final PilotsSnapshotTesterHatModel pilotsSnapshotTesterHatModel;
    private final boolean xmasTextures;

    public SnapshotTesterLayer(RenderLayerParent<S, PlayerModel> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.pilotsSnapshotTesterHatModel = new PilotsSnapshotTesterHatModel(modelSet.bakeLayer(PilotsSnapshotTesterHatModel.LAYER_LOCATION));

        Calendar calendar = Calendar.getInstance();
        this.xmasTextures = (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26);
    }

    protected ResourceLocation getTextureLocation(S renderState) {
        return switch (renderState.getRenderData(MinejagoClientUtils.SNAPSHOT_CHOICE)) {
            case BAMBOO_HAT -> {
                if (xmasTextures)
                    yield PilotsSnapshotTesterHatModel.HOLIDAY_TEXTURE;
                else
                    yield PilotsSnapshotTesterHatModel.TEXTURE;
            }
            case null -> null;
        };
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, S renderState, float p_117353_, float p_117354_) {
        Boolean renderSnapshot = renderState.getRenderData(MinejagoClientUtils.RENDER_SNAPSHOT);
        if (renderSnapshot != null && renderSnapshot) {
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(getTextureLocation(renderState)));
            getParentModel().getHead().translateAndRotate(poseStack);
            switch (renderState.getRenderData(MinejagoClientUtils.SNAPSHOT_CHOICE)) {
                case BAMBOO_HAT -> pilotsSnapshotTesterHatModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
                case null -> {}
            }
        }
    }
}
