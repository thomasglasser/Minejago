package com.thomasglasser.minejago.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thomasglasser.minejago.MinejagoMod;
import com.thomasglasser.minejago.client.entity.model.ThrownBambooStaffModel;
import com.thomasglasser.minejago.server.item.MinejagoItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.*;

public class MinejagoBlockEntityWithoutLevelRenderer extends net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer implements ResourceManagerReloadListener {
    private ThrownBambooStaffModel thrownBambooStaffModel;

    public MinejagoBlockEntityWithoutLevelRenderer() {
        super(null, null);
    }

    public void onResourceManagerReload(ResourceManager pResourceManager) {
        this.thrownBambooStaffModel = new ThrownBambooStaffModel(Minecraft.getInstance().getEntityModels().bakeLayer(ThrownBambooStaffModel.LAYER_LOCATION));
    }

    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pStack.is(MinejagoItems.BAMBOO_STAFF.get())) {
            if (pTransformType == ItemTransforms.TransformType.FIXED || pTransformType == ItemTransforms.TransformType.GROUND || pTransformType == ItemTransforms.TransformType.GUI)
            {
                pPoseStack.translate(0.5D, 0.5D, 0.5D);
                Minecraft.getInstance().getItemRenderer().render(pStack, pTransformType, false, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(MinejagoMod.MODID, "item/bamboo_staff_inventory")));
            }
            else
            {
                pPoseStack.pushPose();
                pPoseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexconsumer1 = ItemRenderer.getFoilBufferDirect(pBuffer, this.thrownBambooStaffModel.renderType(ThrownBambooStaffRenderer.TEXTURE_LOCATION), false, pStack.hasFoil());
                this.thrownBambooStaffModel.renderToBuffer(pPoseStack, vertexconsumer1, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                pPoseStack.popPose();
            }
        }
    }
}