package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.DyeableHorseArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;

public class AbstractHorseArmorLayer<T extends AbstractHorse> extends RenderLayer<T, HorseModel<T>> {
    private final HorseModel<T> model;

    public AbstractHorseArmorLayer(RenderLayerParent<T, HorseModel<T>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new HorseModel<>(entityModelSet.bakeLayer(ModelLayers.HORSE_ARMOR));
    }

    public void render(
            PoseStack matrixStack,
            MultiBufferSource buffer,
            int packedLight,
            T livingEntity,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if (itemStack.getItem() instanceof HorseArmorItem) {
            HorseArmorItem horseArmorItem = (HorseArmorItem)itemStack.getItem();
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            float f;
            float g;
            float h;
            if (horseArmorItem instanceof DyeableHorseArmorItem) {
                int i = ((DyeableHorseArmorItem)horseArmorItem).getColor(itemStack);
                f = (float)(i >> 16 & 0xFF) / 255.0F;
                g = (float)(i >> 8 & 0xFF) / 255.0F;
                h = (float)(i & 0xFF) / 255.0F;
            } else {
                f = 1.0F;
                g = 1.0F;
                h = 1.0F;
            }

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(horseArmorItem.getTexture()));
            this.model.renderToBuffer(matrixStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, f, g, h, 1.0F);
        }
    }
}
