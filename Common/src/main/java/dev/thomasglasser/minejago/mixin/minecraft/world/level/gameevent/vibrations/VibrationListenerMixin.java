package dev.thomasglasser.minejago.mixin.minecraft.world.level.gameevent.vibrations;

import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VibrationListener.class)
public class VibrationListenerMixin
{
    @Inject(method = "getGameEventFrequency", at = @At("HEAD"), cancellable = true)
    private static void minejago_getGameEventFrequency(GameEvent gameEvent, CallbackInfoReturnable<Integer> cir)
    {
        cir.setReturnValue(MinejagoGameEvents.VIBRATION_FREQUENCY_FOR_EVENT.getOrDefault(BuiltInRegistries.GAME_EVENT.getKey(gameEvent), cir.getReturnValue()));
    }
}
