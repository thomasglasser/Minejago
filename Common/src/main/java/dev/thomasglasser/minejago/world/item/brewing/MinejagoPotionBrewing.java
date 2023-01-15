package dev.thomasglasser.minejago.world.item.brewing;

import com.google.common.collect.Lists;
import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class MinejagoPotionBrewing
{
    private static final List<PotionBrewing.Mix<Potion>> TEA_MIXES = Lists.newArrayList();

    public static void addMixes()
    {
        Services.POTION.addMix(TEA_MIXES, Potions.WATER, Ingredient.of(ItemTags.LEAVES), MinejagoPotions.REGULAR_TEA.get());
    }

    public static boolean hasTeaMix(ItemStack pInput, ItemStack pReagent) {
        Potion potion = PotionUtils.getPotion(pInput);
        int i = 0;

        for(int j = TEA_MIXES.size(); i < j; ++i) {
            if (Services.POTION.isTeaMix(TEA_MIXES.get(i), potion, pReagent)) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack mix(ItemStack pReagent, ItemStack pPotion) {
        if (!pPotion.isEmpty()) {
            Potion potion = PotionUtils.getPotion(pPotion);
            Item item = pPotion.getItem();
            int i = 0;

            for(int k = TEA_MIXES.size(); i < k; ++i) {
                PotionBrewing.Mix<Potion> mix1 = TEA_MIXES.get(i);
                if (Services.POTION.isTeaMix(mix1, potion, pReagent)) {
                    return Services.POTION.mix(mix1, pPotion);
                }
            }
        }

        return pPotion;
    }
}
