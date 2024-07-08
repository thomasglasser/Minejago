package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.SpinjitzuModel;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class SpinjitzuLayer<T extends LivingEntity> extends RenderLayer<T, PlayerModel<T>> {
    public static final int DEFAULT = 0x3d99ef;

    private static final ResourceLocation TEXTURE_LOCATION = Minejago.modLoc("textures/entity/player/spinjitzu.png");
    private final SpinjitzuModel<T> model;

    public SpinjitzuLayer(RenderLayerParent<T, PlayerModel<T>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new SpinjitzuModel<>(modelSet.bakeLayer(SpinjitzuModel.LAYER_LOCATION));
    }

    public void render(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            T livingEntity,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {
        if (livingEntity.getData(MinejagoAttachmentTypes.SPINJITZU).active()) {
            float f = (float) livingEntity.tickCount + partialTick;
//            this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.body.copyFrom(getParentModel().body);
            poseStack.translate(0, 1.5, 0);
            this.model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.breezeWind(TEXTURE_LOCATION, this.xOffset(f) % 1.0F, 0.0F)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        }
    }

    private float xOffset(float tickCount) {
        return tickCount * 0.02F;
    }
}
