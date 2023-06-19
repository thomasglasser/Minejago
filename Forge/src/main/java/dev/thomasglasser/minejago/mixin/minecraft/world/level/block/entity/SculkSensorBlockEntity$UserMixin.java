package dev.thomasglasser.minejago.mixin.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.level.block.entity.SculkSensorBlockEntity$VibrationUser")
public class SculkSensorBlockEntity$UserMixin
{
    @Shadow(aliases = "this$0")
    private SculkSensorBlockEntity this$0;

    @Inject(method = "onReceiveVibration", at = @At(shift = At.Shift.AFTER,  value = "INVOKE", target = "Lnet/minecraft/world/level/gameevent/vibrations/VibrationSystem;getGameEventFrequency(Lnet/minecraft/world/level/gameevent/GameEvent;)I"))
    private void minejago_onReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, GameEvent gameEvent, Entity entity, Entity entity2, float f, CallbackInfo ci)
    {
        this$0.setLastVibrationFrequency(VibrationSystem.getGameEventFrequency(gameEvent));
    }
}
