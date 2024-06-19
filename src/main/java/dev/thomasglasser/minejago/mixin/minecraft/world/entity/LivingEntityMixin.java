package dev.thomasglasser.minejago.mixin.minecraft.world.entity;

import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
    private final LivingEntity INSTANCE = (LivingEntity)(Object)this;

    @Inject(method = "doHurtTarget", at = @At("TAIL"))
    private void minejago_doHurtTarget(Entity target, CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValue() && target instanceof ServerPlayer sp && ((INSTANCE.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + EnchantmentHelper.getKnockbackBonus(INSTANCE)) > 2))
            MinejagoEntityEvents.stopSpinjitzu(sp.getData(MinejagoAttachmentTypes.SPINJITZU), sp, true);
    }
}
