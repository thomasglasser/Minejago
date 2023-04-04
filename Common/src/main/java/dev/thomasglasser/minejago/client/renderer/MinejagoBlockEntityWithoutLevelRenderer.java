package dev.thomasglasser.minejago.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.BambooStaffModel;
import dev.thomasglasser.minejago.client.model.ScytheModel;
import dev.thomasglasser.minejago.client.model.SpearModel;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.armor.PoweredArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class MinejagoBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer implements ResourceManagerReloadListener {
    private BambooStaffModel bambooStaffModel;
    private SpearModel spearModel;
    private ScytheModel scytheModel;

    public MinejagoBlockEntityWithoutLevelRenderer() {
        super(null, null);
    }

    public void onResourceManagerReload(ResourceManager manager) {
        this.bambooStaffModel = new BambooStaffModel(Minecraft.getInstance().getEntityModels().bakeLayer(BambooStaffModel.LAYER_LOCATION));
        this.spearModel = new SpearModel(Minecraft.getInstance().getEntityModels().bakeLayer(SpearModel.LAYER_LOCATION));
        this.scytheModel = new ScytheModel(Minecraft.getInstance().getEntityModels().bakeLayer(ScytheModel.LAYER_LOCATION));
    }

    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        if (stack.is(MinejagoItems.IRON_SCYTHE.get())) {
            if (!(displayContext == ItemDisplayContext.GUI || displayContext == ItemDisplayContext.FIXED || displayContext == ItemDisplayContext.GROUND))
            {
                poseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.scytheModel.renderType(Minejago.modLoc("textures/entity/iron_scythe.png")), false, stack.hasFoil());
                this.scytheModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                poseStack.translate(0.5D, 0.5D, 0.5D);
                Services.ITEM.renderItem(stack, displayContext, false, poseStack, buffer, packedLight, packedOverlay, "iron_scythe_inventory");
            }
        }
        else if (stack.is(MinejagoItems.BAMBOO_STAFF.get())) {
            if (!(displayContext == ItemDisplayContext.GUI || displayContext == ItemDisplayContext.FIXED || displayContext == ItemDisplayContext.GROUND))
            {
                poseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.bambooStaffModel.renderType(Minejago.modLoc("textures/entity/bamboo_staff.png")), false, stack.hasFoil());
                this.bambooStaffModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                poseStack.translate(0.5D, 0.5D, 0.5D);
                Services.ITEM.renderItem(stack, displayContext, false, poseStack, buffer, packedLight, packedOverlay, "bamboo_staff_inventory");
            }
        }
        else if (stack.is(MinejagoItems.SCYTHE_OF_QUAKES.get())) {
            if (!(displayContext == ItemDisplayContext.GUI || displayContext == ItemDisplayContext.FIXED || displayContext == ItemDisplayContext.GROUND))
            {
                poseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.scytheModel.renderType(Minejago.modLoc("textures/entity/scythe_of_quakes.png")), false, stack.hasFoil());
                this.scytheModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                poseStack.translate(0.5D, 0.5D, 0.5D);
                Services.ITEM.renderItem(stack, displayContext, false, poseStack, buffer, packedLight, packedOverlay, "scythe_of_quakes_inventory");
            }
        }
        else if (stack.is(MinejagoItems.IRON_SPEAR.get())) {
            if (!(displayContext == ItemDisplayContext.GUI || displayContext == ItemDisplayContext.FIXED || displayContext == ItemDisplayContext.GROUND))
            {
                poseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.spearModel.renderType(Minejago.modLoc("textures/entity/iron_spear.png")), false, stack.hasFoil());
                this.spearModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                poseStack.translate(0.5D, 0.5D, 0.5D);
                Services.ITEM.renderItem(stack, displayContext, false, poseStack, buffer, packedLight, packedOverlay, "iron_spear_inventory");
            }
        }
        else if (stack.getItem() instanceof PoweredArmorItem)
        {
            poseStack.translate(0.5D, 0.5D, 0.5D);
            ResourceLocation location = ResourceLocation.of(stack.getOrCreateTag().getString("Power"), ':');
            final String[] path = new String[1];
            MinejagoArmor.POWER_SETS.forEach(armorSet ->
                    armorSet.getAll().forEach(item ->
                    {
                        if (stack.is(item.get()))
                        {
                            path[0] = item.getId().getPath();
                        }
                    }));
            if (!path[0].isEmpty())
            {
                Services.ITEM.renderItem(stack, displayContext, false, poseStack, buffer, packedLight, packedOverlay, location.getNamespace(), "minejago_armor/" + location.getPath() + "_" + path[0]);
            }
        }
        poseStack.popPose();
    }
}