package dev.thomasglasser.minejago.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.BambooStaffModel;
import dev.thomasglasser.minejago.client.model.IronSpearModel;
import dev.thomasglasser.minejago.client.model.ScytheModel;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownBambooStaffRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownIronSpearRenderer;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.*;

public class MinejagoBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer implements ResourceManagerReloadListener {
    private BambooStaffModel bambooStaffModel;
    private IronSpearModel ironSpearModel;
    private ScytheModel scytheModel;

    public MinejagoBlockEntityWithoutLevelRenderer() {
        super(null, null);
    }

    public void onResourceManagerReload(ResourceManager pResourceManager) {
        this.bambooStaffModel = new BambooStaffModel(Minecraft.getInstance().getEntityModels().bakeLayer(BambooStaffModel.LAYER_LOCATION));
        this.ironSpearModel = new IronSpearModel(Minecraft.getInstance().getEntityModels().bakeLayer(IronSpearModel.LAYER_LOCATION));
        this.scytheModel = new ScytheModel(Minecraft.getInstance().getEntityModels().bakeLayer(ScytheModel.LAYER_LOCATION));
    }

    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pStack.is(MinejagoItems.BAMBOO_STAFF.get())) {
            if (pTransformType == ItemTransforms.TransformType.FIXED || pTransformType == ItemTransforms.TransformType.GROUND || pTransformType == ItemTransforms.TransformType.GUI)
            {
                pPoseStack.translate(0.5D, 0.5D, 0.5D);
                Minecraft.getInstance().getItemRenderer().render(pStack, pTransformType, false, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Minejago.MOD_ID, "item/bamboo_staff_inventory")));
            }
            else
            {
                pPoseStack.pushPose();
                if (!Minecraft.getInstance().player.isUsingItem())
                {
                    pPoseStack.scale(1.0F, -1.0F, -1.0F);
                }
                VertexConsumer vertexconsumer1 = ItemRenderer.getFoilBufferDirect(pBuffer, this.bambooStaffModel.renderType(ThrownBambooStaffRenderer.TEXTURE_LOCATION), false, pStack.hasFoil());
                this.bambooStaffModel.renderToBuffer(pPoseStack, vertexconsumer1, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                pPoseStack.popPose();
            }
        }
        else if (pStack.is(MinejagoItems.IRON_SPEAR.get()))
        {
            if (pTransformType == ItemTransforms.TransformType.FIXED || pTransformType == ItemTransforms.TransformType.GROUND || pTransformType == ItemTransforms.TransformType.GUI)
            {
                pPoseStack.translate(0.5D, 0.5D, 0.5D);
                Minecraft.getInstance().getItemRenderer().render(pStack, pTransformType, false, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Minejago.MOD_ID, "item/iron_spear_inventory")));
            }
            else
            {
                pPoseStack.pushPose();
                if (Minecraft.getInstance().player.isUsingItem() && !pTransformType.firstPerson())
                {
                    pPoseStack.translate(0, -1, 0);
                }
                else
                {
                    pPoseStack.scale(1.0F, -1.0F, -1.0F);
                }
                VertexConsumer vertexconsumer1 = ItemRenderer.getFoilBufferDirect(pBuffer, this.ironSpearModel.renderType(ThrownIronSpearRenderer.TEXTURE_LOCATION), false, pStack.hasFoil());
                this.ironSpearModel.renderToBuffer(pPoseStack, vertexconsumer1, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                pPoseStack.popPose();
            }
        }
        else if (pStack.is(MinejagoItems.SCYTHE_OF_QUAKES.get()))
        {
            if (pTransformType == ItemTransforms.TransformType.FIXED || pTransformType == ItemTransforms.TransformType.GROUND || pTransformType == ItemTransforms.TransformType.GUI)
            {
                pPoseStack.translate(0.5D, 0.5D, 0.5D);
                Minecraft.getInstance().getItemRenderer().render(pStack, pTransformType, false, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Minejago.MOD_ID, "item/scythe_of_quakes_inventory")));
            }
            else
            {
                pPoseStack.pushPose();
                pPoseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexconsumer1 = ItemRenderer.getFoilBufferDirect(pBuffer, this.scytheModel.renderType(new ResourceLocation(Minejago.MOD_ID, "textures/entity/scythe_of_quakes.png")), false, pStack.hasFoil());
                this.scytheModel.renderToBuffer(pPoseStack, vertexconsumer1, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                pPoseStack.popPose();
            }
        }
        else if (pStack.is(MinejagoItems.IRON_SCYTHE.get()))
        {
            if (pTransformType == ItemTransforms.TransformType.FIXED || pTransformType == ItemTransforms.TransformType.GROUND || pTransformType == ItemTransforms.TransformType.GUI)
            {
                pPoseStack.translate(0.5D, 0.5D, 0.5D);
                Minecraft.getInstance().getItemRenderer().render(pStack, pTransformType, false, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Minejago.MOD_ID, "item/iron_scythe_inventory")));
            }
            else
            {
                pPoseStack.pushPose();
                pPoseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexconsumer1 = ItemRenderer.getFoilBufferDirect(pBuffer, this.scytheModel.renderType(new ResourceLocation(Minejago.MOD_ID, "textures/entity/iron_scythe.png")), false, pStack.hasFoil());
                this.scytheModel.renderToBuffer(pPoseStack, vertexconsumer1, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                pPoseStack.popPose();
            }
        }
    }
}