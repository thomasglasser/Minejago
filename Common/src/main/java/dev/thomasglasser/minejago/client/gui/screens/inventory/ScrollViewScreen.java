package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class ScrollViewScreen extends BookViewScreen {
    public static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/scroll.png");

    public ScrollViewScreen(BookAccess bookAccess)
    {
        super(bookAccess);
    }

    public ScrollViewScreen() {
        this(EMPTY_ACCESS);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(guiGraphics, pMouseX, pMouseY, pPartialTick);
        int i = (this.width - 192) / 2;
        int j = 2;
        guiGraphics.blit(BACKGROUND, i, 2, 0, 0, 192, 192);
        if (this.cachedPage != this.currentPage) {
            FormattedText formattedText = this.bookAccess.getPage(this.currentPage);
            this.cachedPageComponents = this.font.split(formattedText, 114);
            this.pageMsg = Component.translatable("book.pageIndicator", this.currentPage + 1, Math.max(this.getNumPages(), 1));
        }

        this.cachedPage = this.currentPage;
        int k = this.font.width(this.pageMsg);
        guiGraphics.drawString(this.font, this.pageMsg, (i - k + 192 - 44), 18, 0);
        int l = Math.min(128 / 9, this.cachedPageComponents.size());

        for(int m = 0; m < l; ++m) {
            FormattedCharSequence formattedCharSequence = (FormattedCharSequence)this.cachedPageComponents.get(m);
            guiGraphics.drawString(this.font, formattedCharSequence, (i + 36), (32 + m * 9), 0);
        }

        Style style = this.getClickedComponentStyleAt((double)pMouseX, (double)pMouseY);
        if (style != null) {
            guiGraphics.renderComponentHoverEffect(this.font, style, pMouseX, pMouseY);
        }
        
        for(Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
        }
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - 192) / 2;
        removeWidget(forwardButton);
        removeWidget(backButton);
        forwardButton = this.addRenderableWidget(new CustomPageButton(i + 131, 154, true, button -> this.pageForward(), true, BACKGROUND));
        backButton = this.addRenderableWidget(new CustomPageButton(i + 28, 154, false, button -> this.pageBack(), true, BACKGROUND));
        updateButtonVisibility();
    }

    public static BookViewScreen.BookAccess fromItem(ItemStack stack) {
        if (stack.is(MinejagoItems.WRITTEN_SCROLL.get())) {
            return new BookViewScreen.WrittenBookAccess(stack);
        } else {
            return stack.is(MinejagoItems.WRITABLE_SCROLL.get()) ? new WritableBookAccess(stack) : BookViewScreen.EMPTY_ACCESS;
        }
    }
}
