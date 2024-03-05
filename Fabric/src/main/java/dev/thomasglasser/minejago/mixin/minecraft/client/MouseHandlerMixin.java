package dev.thomasglasser.minejago.mixin.minecraft.client;

import dev.thomasglasser.minejago.client.MinejagoClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin
{
	@Shadow @Final private Minecraft minecraft;

	@Inject(method = "onPress", at = @At("TAIL"))
	private void minejago_onPress(long windowPointer, int button, int action, int modifiers, CallbackInfo ci)
	{
		if (windowPointer == minecraft.getWindow().getWindow()) MinejagoClientEvents.onInput(-1);
	}

	@Inject(method = "onScroll", at = @At("TAIL"))
	private void minejago_onScroll(long windowPointer, double xOffset, double yOffset, CallbackInfo ci)
	{
		if (windowPointer == minecraft.getWindow().getWindow()) MinejagoClientEvents.onInput(-1);
	}
}
