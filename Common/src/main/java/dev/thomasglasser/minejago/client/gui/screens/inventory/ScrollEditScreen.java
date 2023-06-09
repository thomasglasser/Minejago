package dev.thomasglasser.minejago.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        setFocused(null);
        int i = (width - IMAGE_WIDTH) / 2;
        int j = 2;
        guiGraphics.blit(BACKGROUND, i, j, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        if (isSigning) {
            boolean bl = frameTick / 6 % 2 == 0;
            FormattedCharSequence formattedCharSequence = FormattedCharSequence.composite(
                    FormattedCharSequence.forward(title, Style.EMPTY), bl ? BLACK_CURSOR : GRAY_CURSOR
            );
            int k = font.width(EDIT_TITLE_LABEL);
            guiGraphics.drawString(this.font, EDIT_TITLE_LABEL, (i + 36 + (114 - k) / 2), 34, 0);
            int l = font.width(formattedCharSequence);
            guiGraphics.drawString(this.font, formattedCharSequence, (i + 36 + (114 - l) / 2), 50, 0);
            int m = font.width(ownerText);
            guiGraphics.drawString(this.font, ownerText, (i + 36 + (114 - m) / 2), 60, 0);
            guiGraphics.drawWordWrap(this.font, FINALIZE_WARNING_LABEL, i + 36, 82, 114, 0);
        } else {
            int n = font.width(pageMsg);
            guiGraphics.drawString(this.font, pageMsg, (i - n + IMAGE_WIDTH - 44), 18, 0);
            BookEditScreen.DisplayCache displayCache = getDisplayCache();

            for(BookEditScreen.LineInfo lineInfo : displayCache.lines) {
                guiGraphics.drawString(this.font, lineInfo.asComponent, lineInfo.x, lineInfo.y, -16777216);
            }

            renderHighlight(guiGraphics, displayCache.selection);
            renderCursor(guiGraphics, displayCache.cursor, displayCache.cursorAtEnd);
        }

        for(Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
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
