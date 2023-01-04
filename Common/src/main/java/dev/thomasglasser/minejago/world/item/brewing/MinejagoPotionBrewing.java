package dev.thomasglasser.minejago.world.item.brewing;

import com.google.common.collect.Lists;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.platform.services.IPlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
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
    private static final List<PotionBrewing.Mix> TEA_MIXES = Lists.newArrayList();

    public static void addMixes()
    {
        Services.POTION.addMix(TEA_MIXES, Potions.WATER, Ingredient.of(ItemTags.LEAVES), MinejagoPotions.REGULAR_TEA.get());
    }

    public static boolean hasTeaMix(ItemStack pInput, ItemStack pReagent) {
        Potion potion = PotionUtils.getPotion(pInput);
        int i = 0;

        for(int j = TEA_MIXES.size(); i < j; ++i) {
            PotionBrewing.Mix<Potion> mix = TEA_MIXES.get(i);
            if (mix.from.equals(potion) && mix.ingredient.test(pReagent)) {
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
                if (mix1.from.equals(potion) && mix1.ingredient.test(pReagent)) {
                    Potion to;
                    if (Services.PLATFORM.getPlatformName().equals("Forge"))
                        to = (Potion)((Holder.Reference)((Object)mix1.to)).value();
                    else
                        to = (Potion)(Object)(mix1.to);
                    return PotionUtils.setPotion(new ItemStack(item), to);
                }
            }
        }

        return pPotion;
    }
}
