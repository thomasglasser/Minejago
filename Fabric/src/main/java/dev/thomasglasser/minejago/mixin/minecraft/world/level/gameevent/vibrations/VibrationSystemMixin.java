package dev.thomasglasser.minejago.mixin.minecraft.world.level.gameevent.vibrations;

import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VibrationSystem.class)
public class VibrationSystemMixin
{
    @Inject(method = "getGameEventFrequency", at = @At("HEAD"), cancellable = true)
    private static void minejago_getGameEventFrequency(GameEvent gameEvent, CallbackInfoReturnable<Integer> cir)
    {
        ResourceLocation key = BuiltInRegistries.GAME_EVENT.getKey(gameEvent);
        if (MinejagoGameEvents.VIBRATION_FREQUENCY_FOR_EVENT.containsKey(key)) cir.setReturnValue(MinejagoGameEvents.VIBRATION_FREQUENCY_FOR_EVENT.get(key));
    }
}
