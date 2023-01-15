package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IPotionHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class FabricPotionHelper implements IPotionHelper {
    @Override
    public void addMix(List<PotionBrewing.Mix<Potion>> list, Potion pPotionEntry, Ingredient pPotionIngredient, Potion pPotionResult) {
        list.add(new PotionBrewing.Mix<>(pPotionEntry, pPotionIngredient, pPotionResult));
    }

    @Override
    public boolean isTeaMix(PotionBrewing.Mix<Potion> mix, Potion original, ItemStack reagent) {
        return mix.from == original && mix.ingredient.test(reagent);
    }

    @Override
    public ItemStack mix(PotionBrewing.Mix<Potion> mix, ItemStack potion) {
        Potion to = mix.to;
        return PotionUtils.setPotion(new ItemStack(potion.getItem()), to);
    }
}
