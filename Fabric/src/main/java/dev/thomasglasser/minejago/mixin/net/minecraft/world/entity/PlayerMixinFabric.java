package dev.thomasglasser.minejago.mixin.net.minecraft.world.entity;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixinFabric
{
    @Inject(method = "createAttributes", at = @At("TAIL"), cancellable = true)
    private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir)
    {
        cir.setReturnValue(cir.getReturnValue().add(Attributes.ATTACK_KNOCKBACK));
    }

    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 0)
    private int knockbackHandler(int orig)
    {
        return (int)(((Player)(Object)this).getAttributeValue(Attributes.ATTACK_KNOCKBACK));
    }
}
