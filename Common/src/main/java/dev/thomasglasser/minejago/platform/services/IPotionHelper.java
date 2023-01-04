package dev.thomasglasser.minejago.platform.services;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface IPotionHelper
{
    void addMix(List<PotionBrewing.Mix> list, Potion pPotionEntry, Ingredient pPotionIngredient, Potion pPotionResult);
}
