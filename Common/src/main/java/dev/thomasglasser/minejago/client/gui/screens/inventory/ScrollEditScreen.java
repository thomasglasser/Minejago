package dev.thomasglasser.minejago.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollViewScreen.BACKGROUND;

public class ScrollEditScreen extends BookEditScreen {
    private static final int IMAGE_WIDTH = 192;
    private static final int IMAGE_HEIGHT = 192;
    public static final Component EDIT_TITLE_LABEL = Component.translatable("scroll.editTitle");
    public static final Component FINALIZE_WARNING_LABEL = Component.translatable("scroll.finalizeWarning");

    public ScrollEditScreen(Player player, ItemStack itemStack, InteractionHand interactionHand) {
        super(player, itemStack, interactionHand);
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
    public void init() {
        super.init();
        int i = (this.width - 192) / 2;
        removeWidget(forwardButton);
        removeWidget(backButton);
        forwardButton = this.addRenderableWidget(new CustomPageButton(i + 131, 154, true, button -> this.pageForward(), true, BACKGROUND));
        backButton = this.addRenderableWidget(new CustomPageButton(i + 28, 154, false, button -> this.pageBack(), true, BACKGROUND));
        updateButtonVisibility();
    }
}
