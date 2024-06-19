package dev.thomasglasser.minejago.client.gui.screens.inventory;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;

public class ScrollEditScreen extends BookEditScreen
{
    public static final Component EDIT_TITLE_LABEL = Component.translatable("scroll.edit_title");
    public static final Component FINALIZE_WARNING_LABEL = Component.translatable("scroll.finalize_warning");

    public ScrollEditScreen(Player owner, ItemStack book, InteractionHand hand)
    {
        super(owner, book, hand);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        this.renderTransparentBackground(guiGraphics);
        guiGraphics.blit(ScrollViewScreen.BACKGROUND, (this.width - 192) / 2, 2, 0, 0, 192, 192);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        Iterator var5 = this.renderables.iterator();

        while(var5.hasNext()) {
            Renderable renderable = (Renderable)var5.next();
            renderable.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }
        this.setFocused(null);
        int i = (this.width - 192) / 2;
        int j = 2;
        if (this.isSigning) {
            boolean flag = this.frameTick / 6 % 2 == 0;
            FormattedCharSequence formattedcharsequence = FormattedCharSequence.composite(
                    FormattedCharSequence.forward(this.title, Style.EMPTY), flag ? BLACK_CURSOR : GRAY_CURSOR
            );
            int k = this.font.width(EDIT_TITLE_LABEL);
            pGuiGraphics.drawString(this.font, EDIT_TITLE_LABEL, i + 36 + (114 - k) / 2, 34, 0, false);
            int l = this.font.width(formattedcharsequence);
            pGuiGraphics.drawString(this.font, formattedcharsequence, i + 36 + (114 - l) / 2, 50, 0, false);
            int i1 = this.font.width(this.ownerText);
            pGuiGraphics.drawString(this.font, this.ownerText, i + 36 + (114 - i1) / 2, 60, 0, false);
            pGuiGraphics.drawWordWrap(this.font, FINALIZE_WARNING_LABEL, i + 36, 82, 114, 0);
        } else {
            int j1 = this.font.width(this.pageMsg);
            pGuiGraphics.drawString(this.font, this.pageMsg, i - j1 + 192 - 44, 18, 0, false);
            BookEditScreen.DisplayCache bookeditscreen$displaycache = this.getDisplayCache();

            for (BookEditScreen.LineInfo bookeditscreen$lineinfo : bookeditscreen$displaycache.lines) {
                pGuiGraphics.drawString(this.font, bookeditscreen$lineinfo.asComponent, bookeditscreen$lineinfo.x, bookeditscreen$lineinfo.y, -16777216, false);
            }

            this.renderHighlight(pGuiGraphics, bookeditscreen$displaycache.selection);
            this.renderCursor(pGuiGraphics, bookeditscreen$displaycache.cursor, bookeditscreen$displaycache.cursorAtEnd);
        }
    }
}
