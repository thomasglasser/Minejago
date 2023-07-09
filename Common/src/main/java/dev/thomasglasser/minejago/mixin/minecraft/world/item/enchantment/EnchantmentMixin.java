package dev.thomasglasser.minejago.mixin.minecraft.world.item.enchantment;

import dev.thomasglasser.minejago.world.item.Enchantable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin
{
    @Inject(method = "canEnchant", at = @At(value = "HEAD"), cancellable = true)
    void canEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> callback)
    {
        if (stack.getItem() instanceof Enchantable enchantable && enchantable.canEnchant((Enchantment)(Object)this, stack))
            callback.setReturnValue(true);
    }
}
