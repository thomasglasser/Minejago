package dev.thomasglasser.minejago.mixin.net.minecraft.client.multiplayer;

import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin
{
    @Shadow public abstract void setDayTime(long pTime);

    @Shadow public abstract void gameEvent(GameEvent pEvent, Vec3 pPosition, GameEvent.Context pContext);

    @Shadow @Final private ClientLevel.ClientLevelData clientLevelData;
    private static final Vec3 INVASION_CLOUDS = new Vec3(232, 234, 177);

    @Inject(at = @At("HEAD"), method = "getCloudColor", cancellable = true)
    private void getCloudColor(float pPartialTick, CallbackInfoReturnable<Vec3> callback)
    {
        if (/*TODO: Make world capability boolean for skeleton event*/false)
        {
            Minecraft.getInstance().options.cloudStatus().set(CloudStatus.FAST); //TODO: Fix fancy clouds
            /*TODO: Set time and stop time moving on server when event starts*/ //this.setDayTime(18000);
            callback.setReturnValue(INVASION_CLOUDS);
        }
    }
}
