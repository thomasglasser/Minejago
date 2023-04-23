package dev.thomasglasser.minejago.mixin.minecraft.world.entity;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin
{
    private final ServerPlayer INSTANCE = (ServerPlayer)(Object)this;

    @Inject(method = "attack", at = @At("HEAD"))
    private void minejago_attack(Entity target, CallbackInfo ci)
    {
        if (target instanceof ServerPlayer sp && (INSTANCE.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + EnchantmentHelper.getKnockbackBonus(INSTANCE)) > 2)
            MinejagoEntityEvents.stopSpinjitzu(Services.DATA.getSpinjitzuData(sp), sp, true);
    }

    @Inject(method = "crit", at = @At("HEAD"))
    private void minejago_crit(Entity entityHit, CallbackInfo ci)
    {
        if (entityHit instanceof ServerPlayer sp)
            MinejagoEntityEvents.stopSpinjitzu(Services.DATA.getSpinjitzuData(sp), sp, true);
    }

    @Inject(method = "magicCrit", at = @At("HEAD"))
    private void minejago_magicCrit(Entity entityHit, CallbackInfo ci)
    {
        if (entityHit instanceof ServerPlayer sp)
            MinejagoEntityEvents.stopSpinjitzu(Services.DATA.getSpinjitzuData(sp), sp, true);
    }
}
