package dev.thomasglasser.minejago.mixin.net.minecraft.client.multiplayer;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin
{
    private static final Vec3 INVASION_CLOUDS = new Vec3(232, 234, 177);

    @Inject(at = @At("HEAD"), method = "getCloudColor", cancellable = true)
    private void getCloudColor(float pPartialTick, CallbackInfoReturnable<Vec3> callback)
    {
        if (/*TODO: Make world capability boolean for skeleton event*/true)
        {
            Minecraft.getInstance().options.cloudStatus().set(CloudStatus.FAST); //TODO: Fix fancy clouds
            /*TODO: Set time and stop time moving on server when event starts*/ //this.setDayTime(18000);
            callback.setReturnValue(INVASION_CLOUDS);
        }
    }
}
