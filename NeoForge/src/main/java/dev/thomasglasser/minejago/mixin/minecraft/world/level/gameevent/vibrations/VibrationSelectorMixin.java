package dev.thomasglasser.minejago.mixin.minecraft.world.level.gameevent.vibrations;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VibrationSelector.class)
public class VibrationSelectorMixin
{
    @ModifyExpressionValue(
            method = "shouldReplaceVibration",
            at = @At(value = "TAIL", target = "Lnet/minecraft/world/level/gameevent/vibrations/VibrationSystem;getGameEventFrequency(Lnet/minecraft/world/level/gameevent/GameEvent;)I")
    )
    private int minejago_shouldReplaceVibration(int original, GameEvent event) {
        return Math.max(MinejagoGameEvents.getGameEventFrequency(event), original);
    }
}
