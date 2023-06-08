package dev.thomasglasser.minejago.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.resources.ResourceLocation;

public class CustomPageButton extends PageButton {
    protected ResourceLocation location;

    public CustomPageButton(int i, int j, boolean bl, OnPress onPress, boolean bl2, ResourceLocation rl) {
        super(i, j, bl, onPress, bl2);
        location = rl;
    }

    @Override
    public void renderWidget(PoseStack poseStack, int i, int j, float f) {
        RenderSystem.setShaderTexture(0, location);
        int k = 0;
        int l = 192;
        if (this.isHoveredOrFocused()) {
            k += 23;
        }

        if (!isForward) {
            l += 13;
        }

        blit(poseStack, this.getX(), this.getY(), k, l, 23, 13);
    }
}
