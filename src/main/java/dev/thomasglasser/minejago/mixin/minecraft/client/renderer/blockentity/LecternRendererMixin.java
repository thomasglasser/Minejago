package dev.thomasglasser.minejago.mixin.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.LecternRenderer;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LecternRenderer.class)
public class LecternRendererMixin
{
    @Shadow @Final private BookModel bookModel;

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/LecternBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"), cancellable = true)
    private void render(LecternBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, CallbackInfo ci)
    {
        BlockState blockState = blockEntity.getBlockState();
        if (blockState.getValue(LecternBlock.HAS_BOOK) && blockEntity.getBook().is(MinejagoItemTags.LECTERN_SCROLLS)) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.0625F, 0.5F);
            float f = blockState.getValue(LecternBlock.FACING).getClockWise().toYRot();
            poseStack.mulPose(Axis.YP.rotationDegrees(-f));
            poseStack.mulPose(Axis.ZP.rotationDegrees(67.5F));
            poseStack.translate(0.0F, -0.125F, 0.0F);
            this.bookModel.setupAnim(0.0F, 0.1F, 0.9F, 1.2F);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(Minejago.modLoc("textures/block/entity/lectern_scroll.png")));
            this.bookModel.render(poseStack, vertexConsumer, packedLight, packedOverlay, -1);
            poseStack.popPose();
            ci.cancel();
        }
    }
}
