package dev.thomasglasser.minejago.mixin.minecraft.world.level.gameevent.vibrations;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VibrationSystem.class)
public interface VibrationSystemMixin
{
    @ModifyReturnValue(method = "getGameEventFrequency(Lnet/minecraft/resources/ResourceKey;)I", at = @At("TAIL"))
    private static int minejago_getGameEventFrequency(int original, ResourceKey<GameEvent> event)
    {
        int frequency = MinejagoGameEvents.getGameEventFrequency(event);
        return frequency > 0 ? Math.max(original, frequency) : original;
    }
}
