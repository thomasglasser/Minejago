package dev.thomasglasser.minejago.world.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public interface ITeapotLiquidHolder
{
    int getCups();

    ItemStack getDrained(ItemStack stack);

    default Potion getPotion(ItemStack stack)
    {
        return Potions.WATER;
    }
}
