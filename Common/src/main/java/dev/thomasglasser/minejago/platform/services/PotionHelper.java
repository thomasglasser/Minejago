package dev.thomasglasser.minejago.platform.services;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface PotionHelper
{
    void addMix(List<PotionBrewing.Mix<Potion>> list, Potion pPotionEntry, Ingredient pPotionIngredient, Potion pPotionResult);

    boolean isTeaMix(PotionBrewing.Mix<Potion> mix, Potion original, ItemStack reagent);

    ItemStack mix(PotionBrewing.Mix<Potion> mix, ItemStack potion);
}
