package dev.thomasglasser.minejago.mixin.minecraft.client.gui;

import dev.thomasglasser.minejago.client.gui.Guis;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin
{
	@Shadow private int tickCount;

	@Inject(method = "renderPlayerHealth", at = @At("TAIL"))
	private void minejago_renderPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci)
	{
		Guis.renderFocusBar(guiGraphics, tickCount);
	}
}
