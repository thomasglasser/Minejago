package dev.thomasglasser.minejago.mixin.minecraft.client.multiplayer;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.thomasglasser.minejago.client.MinejagoClientEvents;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {
    @ModifyExpressionValue(method = "tickTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private boolean tickTime(boolean original) {
        if (MinejagoClientEvents.hasSkulkinRaid())
            return false;
        return original;
    }
}
