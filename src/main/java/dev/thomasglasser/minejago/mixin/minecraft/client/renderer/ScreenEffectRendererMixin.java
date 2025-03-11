package dev.thomasglasser.minejago.mixin.minecraft.client.renderer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {
    @WrapOperation(method = "renderScreenEffect", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;noPhysics:Z"))
    private static boolean renderScreenEffect(Player instance, Operation<Boolean> original) {
        if (instance.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent())
            return false;
        return original.call(instance);
    }
}
