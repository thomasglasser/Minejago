package dev.thomasglasser.minejago.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.BambooStaffModel;
import dev.thomasglasser.minejago.client.model.ScytheModel;
import dev.thomasglasser.minejago.client.model.SpearModel;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownBambooStaffRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownIronSpearRenderer;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class MinejagoBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer implements ResourceManagerReloadListener {
    private BambooStaffModel bambooStaffModel;
    private SpearModel spearModel;
    private ScytheModel scytheModel;

    public MinejagoBlockEntityWithoutLevelRenderer() {
        super(null, null);
    }

    public void onResourceManagerReload(ResourceManager pResourceManager) {
        this.bambooStaffModel = new BambooStaffModel(Minecraft.getInstance().getEntityModels().bakeLayer(BambooStaffModel.LAYER_LOCATION));
        this.spearModel = new SpearModel(Minecraft.getInstance().getEntityModels().bakeLayer(SpearModel.LAYER_LOCATION));
        this.scytheModel = new ScytheModel(Minecraft.getInstance().getEntityModels().bakeLayer(ScytheModel.LAYER_LOCATION));
    }

    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if (stack.is(MinejagoItems.IRON_SCYTHE.get())) {
            if (!(transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.FIXED || transformType == ItemTransforms.TransformType.GROUND))
            {
                poseStack.pushPose();
                poseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.scytheModel.renderType(Minejago.modLoc("textures/entity/iron_scythe.png")), true, stack.hasFoil());
                this.scytheModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            }
            else
            {
                poseStack.translate(0.5D, 0.5D, 0.5D);
                Services.ITEM.forgeRenderItem(stack, transformType, false, poseStack, buffer, packedLight, packedOverlay, Minejago.modLoc("item/iron_scythe_inventory"));
            }
        }
        else if (stack.is(MinejagoItems.BAMBOO_STAFF.get())) {
            if (!(transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.FIXED || transformType == ItemTransforms.TransformType.GROUND))
            {
                poseStack.pushPose();
                poseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.bambooStaffModel.renderType(Minejago.modLoc("textures/entity/bamboo_staff.png")), true, stack.hasFoil());
                this.bambooStaffModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            }
            else
            {
                poseStack.translate(0.5D, 0.5D, 0.5D);
                Services.ITEM.forgeRenderItem(stack, transformType, false, poseStack, buffer, packedLight, packedOverlay, Minejago.modLoc("item/bamboo_staff_inventory"));
            }
        }
        else if (stack.is(MinejagoItems.SCYTHE_OF_QUAKES.get())) {
            if (!(transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.FIXED || transformType == ItemTransforms.TransformType.GROUND))
            {
                poseStack.pushPose();
                poseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.scytheModel.renderType(Minejago.modLoc("textures/entity/scythe_of_quakes.png")), true, stack.hasFoil());
                this.scytheModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            }
            else
            {
                poseStack.translate(0.5D, 0.5D, 0.5D);
                Services.ITEM.forgeRenderItem(stack, transformType, false, poseStack, buffer, packedLight, packedOverlay, Minejago.modLoc("item/scythe_of_quakes_inventory"));
            }
        }
        else if (stack.is(MinejagoItems.IRON_SPEAR.get())) {
            if (!(transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.FIXED || transformType == ItemTransforms.TransformType.GROUND))
            {
                poseStack.pushPose();
                poseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.spearModel.renderType(Minejago.modLoc("textures/entity/iron_spear.png")), true, stack.hasFoil());
                this.spearModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            }
            else
            {
                poseStack.translate(0.5D, 0.5D, 0.5D);
                Services.ITEM.forgeRenderItem(stack, transformType, false, poseStack, buffer, packedLight, packedOverlay, Minejago.modLoc("item/iron_spear_inventory"));
            }
        }
    }
}