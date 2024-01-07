package dev.thomasglasser.minejago.mixin.minecraft.world.level.block.entity;

import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = "net.minecraft.world.level.block.entity.CalibratedSculkSensorBlockEntity$VibrationUser")
public class CalibratedSculkSensorBlockEntityMixin
{
    @Inject(method = "canReceiveVibration", at = @At(shift = At.Shift.AFTER,  value = "INVOKE", target = "Lnet/minecraft/world/level/gameevent/vibrations/VibrationSystem;getGameEventFrequency(Lnet/minecraft/world/level/gameevent/GameEvent;)I"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void minejago_canReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, GameEvent gameEvent, GameEvent.Context context, CallbackInfoReturnable<Boolean> cir, int i)
    {
        if (MinejagoGameEvents.VIBRATION_FREQUENCY_FOR_EVENT.containsKey(BuiltInRegistries.GAME_EVENT.getKey(gameEvent)) && i != 0 && MinejagoGameEvents.getGameEventFrequency(gameEvent) != i)
            cir.setReturnValue(false);
    }
}
