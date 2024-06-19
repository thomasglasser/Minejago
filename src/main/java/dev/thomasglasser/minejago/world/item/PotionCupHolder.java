package dev.thomasglasser.minejago.world.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

public interface PotionCupHolder
{
    int getCups();

    ItemStack getDrained(ItemStack stack);

    default Holder<Potion> getPotion(ItemStack stack)
    {
        return Potions.WATER;
    }

    default boolean canBeFilled(ItemStack stack, Holder<Potion> potion, int cups)
    {
        return cups >= getCups();
    }

    default boolean canBeDrained(ItemStack stack)
    {
        return false;
    }

    ItemStack getFilled(Holder<Potion> potion);
}
