package dev.thomasglasser.minejago.mixin.minecraft.world.level.gameevent.vibrations;

import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import net.minecraft.world.level.gameevent.vibrations.VibrationInfo;
import net.minecraft.world.level.gameevent.vibrations.VibrationSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(VibrationSelector.class)
public class VibrationSelectorMixin
{
    @Inject(method = "shouldReplaceVibration", at = @At("TAIL"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void minejago_shouldReplaceVibration(VibrationInfo vibrationInfo, long tick, CallbackInfoReturnable<Boolean> cir, VibrationInfo currentInfo)
    {
        cir.setReturnValue(MinejagoGameEvents.getGameEventFrequency(vibrationInfo.gameEvent()) > MinejagoGameEvents.getGameEventFrequency(currentInfo.gameEvent()));
    }
}
