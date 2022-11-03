package dev.thomasglasser.minejago.world.item.brewing;

import com.google.common.collect.Lists;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class MinejagoPotionBrewing
{
    private static final List<PotionBrewing.Mix<Potion>> TEA_MIXES = Lists.newArrayList();

    public static void addMixes()
    {
        addMix(Potions.WATER, ItemTags.LEAVES, MinejagoPotions.REGULAR_TEA.get());
    }

    public static void addMix(Potion pPotionEntry, TagKey<Item> pPotionIngredient, Potion pPotionResult)
    {
        TEA_MIXES.add(new PotionBrewing.Mix<>(ForgeRegistries.POTIONS, pPotionEntry, Ingredient.of(pPotionIngredient), pPotionResult));
    }

    public static boolean hasTeaMix(ItemStack pInput, ItemStack pReagent) {
        Potion potion = PotionUtils.getPotion(pInput);
        int i = 0;

        for(int j = TEA_MIXES.size(); i < j; ++i) {
            PotionBrewing.Mix<Potion> mix = TEA_MIXES.get(i);
            if (mix.from.get() == potion && mix.ingredient.test(pReagent)) {
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
                if (mix1.from.get() == potion && mix1.ingredient.test(pReagent)) {
                    return PotionUtils.setPotion(new ItemStack(item), mix1.to.get());
                }
            }
        }

        return pPotion;
    }
}
