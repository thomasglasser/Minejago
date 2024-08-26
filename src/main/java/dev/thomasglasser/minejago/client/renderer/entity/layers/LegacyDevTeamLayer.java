package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class LegacyDevTeamLayer<T extends LivingEntity> extends RenderLayer<T, PlayerModel<T>> {
    private static final ResourceLocation TEXTURE = Minejago.modLoc("textures/entity/player/legacy_dev_team_beard.png");

    private final LegacyDevTeamBeardModel model;

    public LegacyDevTeamLayer(RenderLayerParent<T, PlayerModel<T>> renderer, EntityModelSet modelSet) {
        super(renderer);

        this.model = new LegacyDevTeamBeardModel(modelSet.bakeLayer(LegacyDevTeamBeardModel.LAYER_LOCATION));
    }

    @Override
    protected ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));

        poseStack.translate(0, -0.055, 0);
        if (!(entity instanceof AbstractClientPlayer) || MinejagoClientUtils.renderLegacyDevLayer((AbstractClientPlayer) entity))
            model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
    }
}
