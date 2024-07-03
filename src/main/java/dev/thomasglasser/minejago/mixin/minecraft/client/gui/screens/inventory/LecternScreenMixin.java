package dev.thomasglasser.minejago.mixin.minecraft.client.gui.screens.inventory;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollViewScreen;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.LecternScreen;
import net.minecraft.world.inventory.LecternMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LecternScreen.class)
public abstract class LecternScreenMixin extends BookViewScreen {
    @Shadow
    @Final
    private LecternMenu menu;

    @Shadow
    protected abstract void sendButtonClick(int pageData);

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (menu.getBook().is(MinejagoItemTags.LECTERN_SCROLLS)) {
            this.renderTransparentBackground(guiGraphics);
            guiGraphics.blit(ScrollViewScreen.BACKGROUND, (this.width - 192) / 2, 2, 0, 0, 192, 192);
        } else {
            super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @ModifyExpressionValue(method = "createMenuControls", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;builder(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)Lnet/minecraft/client/gui/components/Button$Builder;", ordinal = 1))
    private Button.Builder createMenuControls(Button.Builder original) {
        if (menu.getBook().is(MinejagoItemTags.LECTERN_SCROLLS)) {
            return Button.builder(ScrollViewScreen.TAKE_SCROLL, (button) -> {
                sendButtonClick(3);
            });
        }
        return original;
    }
}
