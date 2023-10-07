package dev.thomasglasser.minejago.mixin.minecraft.client.multiplayer;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public abstract class MinejagoClientLevelMixin
{
    @Unique
    private static final Vec3 INVASION_CLOUDS = new Vec3(232, 234, 177);

    @Inject(at = @At("HEAD"), method = "getCloudColor", cancellable = true)
    private void Minejago_getCloudColor(float pPartialTick, CallbackInfoReturnable<Vec3> callback)
    {
        if (/*TODO: Make world boolean for skeleton event*/false)
        {
            callback.setReturnValue(INVASION_CLOUDS);
        }
    }
}
