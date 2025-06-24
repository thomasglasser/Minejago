package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.thomasglasser.tommylib.api.world.entity.projectile.ThrownSword;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemDisplayContext;

public class SpinningThrownSwordItemRenderer<T extends ThrownSword & ItemSupplier> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public SpinningThrownSwordItemRenderer(EntityRendererProvider.Context context, float scale, boolean fullBright) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.scale = scale;
        this.fullBright = fullBright;
    }

    public SpinningThrownSwordItemRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F, false);
    }

    @Override
    protected int getBlockLightLevel(T entity, BlockPos pos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(entity, pos);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.tickCount >= 2) {
            poseStack.pushPose();
            poseStack.scale(this.scale, this.scale, this.scale);
            poseStack.translate(0, 0.2, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) + 90));
            poseStack.mulPose(Axis.ZN.rotationDegrees(entity.isInGround() ? 180 : entity.tickCount * 100));
            this.itemRenderer
                    .renderStatic(
                            entity.getItem(),
                            ItemDisplayContext.GROUND,
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            poseStack,
                            buffer,
                            entity.level(),
                            entity.getId());
            poseStack.popPose();
            super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
