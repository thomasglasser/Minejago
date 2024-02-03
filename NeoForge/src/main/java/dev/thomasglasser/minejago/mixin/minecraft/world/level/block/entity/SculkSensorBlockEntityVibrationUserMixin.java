package dev.thomasglasser.minejago.mixin.minecraft.world.level.block.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.level.block.entity.SculkSensorBlockEntity$VibrationUser")
public class SculkSensorBlockEntityVibrationUserMixin
{
    @ModifyExpressionValue(
            method = "onReceiveVibration",
            at = @At(value = "TAIL", target = "Lnet/minecraft/world/level/gameevent/vibrations/VibrationSystem;getGameEventFrequency(Lnet/minecraft/world/level/gameevent/GameEvent;)I")
    )
    private int minejago_onReceiveVibration(int original, GameEvent event) {
        return Math.max(MinejagoGameEvents.getGameEventFrequency(event), original);
    }
}
