package dev.thomasglasser.minejago.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.CommonColors;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ScrollEditScreen extends BookEditScreen {
    public static final Component EDIT_TITLE_LABEL = Component.translatable("scroll.edit_title");
    public static final Component FINALIZE_WARNING_LABEL = Component.translatable("scroll.finalize_warning");

    private int topLeftX;
    private int topLeftY;

    public ScrollEditScreen(Player owner, ItemStack book, InteractionHand hand) {
        super(owner, book, hand);
    }

    @Override
    public void init() {
        super.init();
        this.topLeftX = (this.width - 192) / 2;
        this.topLeftY = 2;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        guiGraphics.blit(ScrollViewScreen.BACKGROUND, topLeftX, topLeftY, 0, 0, 192, 192);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        this.setFocused(null);
        if (this.isSigning) {
            boolean blink = this.frameTick / 6 % 2 == 0;
            int titleLabelWidth = this.font.width(EDIT_TITLE_LABEL);
            guiGraphics.drawString(this.font, EDIT_TITLE_LABEL, topLeftX + 36 + (114 - titleLabelWidth) / 2, 34, 0, false);
            FormattedCharSequence titleLine = FormattedCharSequence.composite(FormattedCharSequence.forward(this.title, Style.EMPTY), blink ? BLACK_CURSOR : GRAY_CURSOR);
            int titleLineWidth = this.font.width(titleLine);
            guiGraphics.drawString(this.font, titleLine, topLeftX + 36 + (114 - titleLineWidth) / 2, 50, 0, false);
            int ownerTextWidth = this.font.width(this.ownerText);
            guiGraphics.drawString(this.font, this.ownerText, topLeftX + 36 + (114 - ownerTextWidth) / 2, 60, 0, false);
            guiGraphics.drawWordWrap(this.font, FINALIZE_WARNING_LABEL, topLeftX + 36, 82, 114, 0);
        } else {
            int msgWidth = this.font.width(this.pageMsg);
            guiGraphics.drawString(this.font, this.pageMsg, topLeftX - msgWidth + 192 - 44, 18, 0, false);

            BookEditScreen.DisplayCache displayCache = this.getDisplayCache();
            for (BookEditScreen.LineInfo lineInfo : displayCache.lines) {
                guiGraphics.drawString(this.font, lineInfo.asComponent, lineInfo.x, lineInfo.y, CommonColors.BLACK, false);
            }
            this.renderHighlight(guiGraphics, displayCache.selection);
            this.renderCursor(guiGraphics, displayCache.cursor, displayCache.cursorAtEnd);
        }
    }
}
