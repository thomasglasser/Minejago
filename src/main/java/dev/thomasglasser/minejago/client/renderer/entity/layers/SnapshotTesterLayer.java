package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.client.model.PilotsSnapshotTesterHatModel;
import java.util.Calendar;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class SnapshotTesterLayer<S extends AbstractClientPlayer> extends RenderLayer<S, PlayerModel<S>> {
    private final PilotsSnapshotTesterHatModel pilotsSnapshotTesterHatModel;
    private final boolean xmasTextures;

    public SnapshotTesterLayer(RenderLayerParent<S, PlayerModel<S>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.pilotsSnapshotTesterHatModel = new PilotsSnapshotTesterHatModel(modelSet.bakeLayer(PilotsSnapshotTesterHatModel.LAYER_LOCATION));

        Calendar calendar = Calendar.getInstance();
        this.xmasTextures = (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26);
    }

    @Override
    protected ResourceLocation getTextureLocation(S player) {
        return switch (MinejagoClientUtils.snapshotChoice(player)) {
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
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, S player, float v, float v1, float v2, float v3, float v4, float v5) {
        if (MinejagoClientUtils.renderSnapshotTesterLayer(player)) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(player)));
            getParentModel().getHead().translateAndRotate(poseStack);
            switch (MinejagoClientUtils.snapshotChoice(player)) {
                case BAMBOO_HAT -> pilotsSnapshotTesterHatModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
                case null -> {}
            }
        }
    }
}
