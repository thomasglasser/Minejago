package dev.thomasglasser.minejago.world.entity.powers;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PowerUtils
{
    public static ItemStack setPower(ItemStack stack, ResourceKey<Power> power)
    {
        stack.getOrCreateTag().putString("Power", power.location().toString());
        return stack;
    }

    public static ResourceKey<Power> getPower(ItemStack stack)
    {
        return ResourceKey.create(MinejagoRegistries.POWER, ResourceLocation.of(stack.getOrCreateTag().getString("Power"), ':'));
    }
}
