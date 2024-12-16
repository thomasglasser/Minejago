package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ScrollViewScreen extends BookViewScreen {
    public static final Component TAKE_SCROLL = Component.translatable("lectern.take_scroll");
    public static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/scroll.png");

    public ScrollViewScreen(BookAccess bookAccess) {
        super(bookAccess);
    }

    public ScrollViewScreen() {
        this(EMPTY_ACCESS);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        guiGraphics.blit(BACKGROUND, (this.width - 192) / 2, 2, 0.0F, 0.0F, 192, 192, 256, 256);
    }
}
