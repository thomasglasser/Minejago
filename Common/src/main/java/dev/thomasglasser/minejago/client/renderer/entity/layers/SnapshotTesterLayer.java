package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientConfig;
import dev.thomasglasser.minejago.client.model.PilotsSnapshotTesterHatModel;
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
import net.minecraft.world.entity.LivingEntity;

import java.util.Calendar;

public class SnapshotTesterLayer<T extends LivingEntity> extends RenderLayer<T, PlayerModel<T>> {
    private static final ResourceLocation BAMBOO_HAT_TEXTURE = Minejago.modLoc("textures/entity/player/pilots_snapshot_hat.png");
    private static final ResourceLocation HOLIDAY_HAT_TEXTURE = Minejago.modLoc("textures/entity/player/pilots_snapshot_holiday_hat.png");

    private final PilotsSnapshotTesterHatModel pilotsSnapshotTesterHatModel;
    private final boolean xmasTextures;

    public SnapshotTesterLayer(RenderLayerParent<T, PlayerModel<T>> renderer, EntityModelSet modelSet) {
        super(renderer);

        this.pilotsSnapshotTesterHatModel = new PilotsSnapshotTesterHatModel(modelSet.bakeLayer(PilotsSnapshotTesterHatModel.LAYER_LOCATION));

        Calendar calendar = Calendar.getInstance();
        this.xmasTextures = (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26);
    }

    @Override
    protected ResourceLocation getTextureLocation(T entity) {
        return switch (MinejagoClientConfig.snapshotTesterCosmeticChoice)
        {

            case BAMBOO_HAT ->
            {
                if (xmasTextures)
                    yield HOLIDAY_HAT_TEXTURE;
                else
                    yield BAMBOO_HAT_TEXTURE;
            }
        };
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));

        getParentModel().getHead().translateAndRotate(poseStack);
        if (entity instanceof AbstractClientPlayer player)
        {
            if (MinejagoClientUtils.renderSnapshotTesterLayer(player))
            {
                switch (MinejagoClientUtils.snapshotChoice(player))
                {
                    case BAMBOO_HAT -> pilotsSnapshotTesterHatModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        }
        else
            pilotsSnapshotTesterHatModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
