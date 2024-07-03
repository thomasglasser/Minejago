package dev.thomasglasser.minejago.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.resources.ResourceLocation;

public class CustomPageButton extends PageButton {
    protected ResourceLocation location;

    public CustomPageButton(int i, int j, boolean bl, OnPress onPress, boolean bl2, ResourceLocation rl) {
        super(i, j, bl, onPress, bl2);
        location = rl;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        int k = 0;
        int l = 192;
        if (this.isHoveredOrFocused()) {
            k += 23;
        }

        if (!isForward) {
            l += 13;
        }

        guiGraphics.blit(location, this.getX(), this.getY(), k, l, 23, 13);
    }
}
