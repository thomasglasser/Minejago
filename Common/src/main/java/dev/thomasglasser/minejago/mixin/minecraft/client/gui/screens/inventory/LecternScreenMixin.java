package dev.thomasglasser.minejago.mixin.minecraft.client.gui.screens.inventory;

import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollViewScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.LecternScreen;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.LecternMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LecternScreen.class)
public class LecternScreenMixin extends BookViewScreen
{
	@Shadow
	@Final
	private LecternMenu menu;

	@Override
	public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
	{
		if (menu.getBook().is(ItemTags.LECTERN_BOOKS))
		{
			this.renderTransparentBackground(guiGraphics);
			guiGraphics.blit(ScrollViewScreen.BACKGROUND, (this.width - 192) / 2, 2, 0, 0, 192, 192);
		}
		else
		{
			super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		}
	}
}
