package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
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
import net.minecraft.world.entity.EquipmentSlot;

public class SnapshotTesterLayer<T extends AbstractClientPlayer> extends RenderLayer<T, PlayerModel<T>> {
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
        return switch (MinejagoClientUtils.snapshotChoice(entity)) {
            case BAMBOO_HAT -> {
                if (xmasTextures)
                    yield HOLIDAY_HAT_TEXTURE;
                else
                    yield BAMBOO_HAT_TEXTURE;
            }
            case null -> null;
        };
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));

        getParentModel().getHead().translateAndRotate(poseStack);
        switch (MinejagoClientUtils.snapshotChoice(entity)) {
            case BAMBOO_HAT -> {
                if (MinejagoClientUtils.renderSnapshotTesterLayer(entity, EquipmentSlot.HEAD)) {
                    pilotsSnapshotTesterHatModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
                }
            }
            case null -> {}
        }
    }
}
