package dev.thomasglasser.minejago.mixin.minecraft.world.entity;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixinFabric
{
    private final Entity entity = (Entity)(Object)this;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci)
    {
        if (entity instanceof LivingEntity livingEntity)
        {
            MinejagoEntityEvents.onLivingTick(livingEntity);
        }
        if (entity instanceof Player player)
        {
            MinejagoEntityEvents.onPlayerTick(player);
        }
    }
}
