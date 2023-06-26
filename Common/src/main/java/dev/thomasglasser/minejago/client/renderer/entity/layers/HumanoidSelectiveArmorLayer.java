package dev.thomasglasser.minejago.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class HumanoidSelectiveArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    public boolean renderHead = true;
    public boolean renderChest = true;
    public boolean renderLegs = true;
    public boolean renderFeet = true;

    public HumanoidSelectiveArmorLayer(RenderLayerParent<T, M> renderLayerParent, A humanoidModel, A humanoidModel2, ModelManager modelManager) {
        super(renderLayerParent, humanoidModel, humanoidModel2, modelManager);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (renderHead)
            this.renderArmorPiece(matrixStack, buffer, livingEntity, EquipmentSlot.HEAD, packedLight, this.getArmorModel(EquipmentSlot.HEAD));
        if (renderChest)
            this.renderArmorPiece(matrixStack, buffer, livingEntity, EquipmentSlot.CHEST, packedLight, this.getArmorModel(EquipmentSlot.CHEST));
        if (renderLegs)
            this.renderArmorPiece(matrixStack, buffer, livingEntity, EquipmentSlot.LEGS, packedLight, this.getArmorModel(EquipmentSlot.LEGS));
        if (renderFeet)
            this.renderArmorPiece(matrixStack, buffer, livingEntity, EquipmentSlot.FEET, packedLight, this.getArmorModel(EquipmentSlot.FEET));
    }
}
