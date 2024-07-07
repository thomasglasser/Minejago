package dev.thomasglasser.minejago.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.thomasglasser.minejago.client.model.BambooStaffModel;
import dev.thomasglasser.minejago.client.model.ScytheModel;
import dev.thomasglasser.minejago.client.model.SpearModel;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.armor.PoweredArmorItem;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.renderer.entity.ThrownSwordRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
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
            poseStack.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.scytheModel.renderType(ThrownSwordRenderer.TEXTURE.apply(MinejagoItems.IRON_SCYTHE.getId())), false, stack.hasFoil());
            this.scytheModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
        } else if (stack.is(MinejagoItems.BAMBOO_STAFF.get())) {
            poseStack.scale(1.0F, -1.0F, -1.0F);
            poseStack.translate(0.0D, -0.7D, 0.0D);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.bambooStaffModel.renderType(ThrownSwordRenderer.TEXTURE.apply(MinejagoItems.BAMBOO_STAFF.getId())), false, stack.hasFoil());
            this.bambooStaffModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, -1);
        } else if (stack.is(MinejagoItems.SCYTHE_OF_QUAKES.get())) {
            poseStack.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.scytheModel.renderType(ThrownSwordRenderer.TEXTURE.apply(MinejagoItems.SCYTHE_OF_QUAKES.getId())), false, stack.hasFoil());
            this.scytheModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
        } else if (stack.is(MinejagoItems.IRON_SPEAR.get())) {
            poseStack.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, this.spearModel.renderType(ThrownSwordRenderer.TEXTURE.apply(MinejagoItems.IRON_SPEAR.getId())), false, stack.hasFoil());
            this.spearModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
        } else if (stack.getItem() instanceof PoweredArmorItem) {
            poseStack.translate(0.5D, 0.5D, 0.5D);
            ResourceLocation location = stack.getOrDefault(MinejagoDataComponents.POWER.get(), MinejagoPowers.NONE.location());
            final String[] path = new String[1];
            MinejagoArmors.POWER_SETS.forEach(armorSet -> armorSet.getAll().forEach(item -> {
                if (stack.is(item.get())) {
                    path[0] = item.getId().getPath();
                }
            }));
            if (!path[0].isEmpty()) {
                ClientUtils.renderItem(stack, displayContext, false, poseStack, buffer, packedLight, packedOverlay, location.getNamespace(), "minejago_armor/" + location.getPath() + "_" + path[0]);
            }
        }
        poseStack.popPose();
    }
}
