package dev.thomasglasser.minejago.client.gui.screens.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.resources.ResourceLocation;
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
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        if ((this.minecraft != null ? this.minecraft.level : null) != null)
        {
            this.renderTransparentBackground(guiGraphics);
        }
        else
        {
            this.renderDirtBackground(guiGraphics);
        }
        guiGraphics.blit(BACKGROUND, (this.width - 192) / 2, 2, 0, 0, 192, 192);
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
