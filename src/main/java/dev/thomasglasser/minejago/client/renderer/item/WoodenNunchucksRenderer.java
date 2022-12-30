package dev.thomasglasser.minejago.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.WoodenNunchucksItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class WoodenNunchucksRenderer extends GeoItemRenderer<WoodenNunchucksItem> {
    public WoodenNunchucksRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("nunchucks")));
    }


    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (renderNormal(transformType))
        {
            poseStack.translate(0.5D, 0.5D, 0.5D);
            Minecraft.getInstance().getItemRenderer().render(stack, transformType, false, poseStack, bufferSource, packedLight, packedOverlay, Minecraft.getInstance().getModelManager().getModel(Minejago.modLoc("item/wooden_nunchucks_inventory")));
        }
        else
            super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }

    @Override
    protected void renderInGui(ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.translate(0.5D, 0.5D, 0.5D);
        Minecraft.getInstance().getItemRenderer().render(currentItemStack, transformType, false, poseStack, bufferSource, packedLight, packedOverlay, Minecraft.getInstance().getModelManager().getModel(Minejago.modLoc("item/wooden_nunchucks_inventory")));
    }

    @Override
    public ResourceLocation getTextureLocation(WoodenNunchucksItem animatable) {
        return Minejago.modLoc("textures/item/geo/wooden_nunchucks.png");
    }

    private static boolean renderNormal(ItemTransforms.TransformType transformType) {
        return transformType == ItemTransforms.TransformType.FIXED || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.GUI;
    }
}
