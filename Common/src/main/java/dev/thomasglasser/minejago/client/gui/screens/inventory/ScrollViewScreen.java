package dev.thomasglasser.minejago.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class ScrollViewScreen extends BookViewScreen {
    public static final ResourceLocation BACKGROUND = Minejago.modLoc("textures/gui/scroll.png");

    public ScrollViewScreen(BookAccess bookAccess)
    {
        super(bookAccess);
    }

    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = (this.width - 192) / 2;
        int j = 2;
        blit(pPoseStack, i, 2, 0, 0, 192, 192);
        if (this.cachedPage != this.currentPage) {
            FormattedText formattedText = this.bookAccess.getPage(this.currentPage);
            this.cachedPageComponents = this.font.split(formattedText, 114);
            this.pageMsg = Component.translatable("book.pageIndicator", this.currentPage + 1, Math.max(this.getNumPages(), 1));
        }

        this.cachedPage = this.currentPage;
        int k = this.font.width(this.pageMsg);
        this.font.draw(pPoseStack, this.pageMsg, (float)(i - k + 192 - 44), 18.0F, 0);
        int l = Math.min(128 / 9, this.cachedPageComponents.size());

        for(int m = 0; m < l; ++m) {
            FormattedCharSequence formattedCharSequence = (FormattedCharSequence)this.cachedPageComponents.get(m);
            this.font.draw(pPoseStack, formattedCharSequence, (float)(i + 36), (float)(32 + m * 9), 0);
        }

        Style style = this.getClickedComponentStyleAt((double)pMouseX, (double)pMouseY);
        if (style != null) {
            this.renderComponentHoverEffect(pPoseStack, style, pMouseX, pMouseY);
        }
        
        for(Renderable renderable : this.renderables) {
            renderable.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
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
}
