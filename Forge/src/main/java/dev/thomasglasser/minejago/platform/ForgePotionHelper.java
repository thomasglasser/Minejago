package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IPotionHelper;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class ForgePotionHelper implements IPotionHelper
{

    @Override
    public void addMix(List<PotionBrewing.Mix> list, Potion pPotionEntry, Ingredient pPotionIngredient, Potion pPotionResult) {
        list.add(new PotionBrewing.Mix<>(ForgeRegistries.POTIONS, pPotionEntry, pPotionIngredient, pPotionResult));
    }
}
