package dev.thomasglasser.minejago.client.renderer.item;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.item.NunchucksItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class WoodenNunchucksRenderer extends GeoItemRenderer<NunchucksItem> {
    public WoodenNunchucksRenderer() {
        super(new DefaultedItemGeoModel<>(Minejago.modLoc("nunchucks")));
    }

    @Override
    public ResourceLocation getTextureLocation(NunchucksItem animatable) {
        return Minejago.modLoc("textures/item/geo/wooden_nunchucks.png");
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
    {
        if (displayContext == ItemDisplayContext.GUI || displayContext == ItemDisplayContext.FIXED || displayContext == ItemDisplayContext.GROUND)
        {
            if (!(displayContext == ItemDisplayContext.GROUND)) poseStack.translate(0.0D, 0.5D, 0.0D);
            poseStack.translate(0.5D, 0.5D, 0.5D);
            poseStack.mulPose(Axis.YN.rotationDegrees(90));
            poseStack.mulPose(Axis.ZN.rotationDegrees(0.1f));
            if (displayContext == ItemDisplayContext.GUI) Lighting.setupForFlatItems();
            Services.ITEM.renderItem(stack, displayContext, false, poseStack, bufferSource, packedLight, packedOverlay, "wooden_nunchucks_inventory");
        }
        else
            super.renderByItem(stack, displayContext, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
