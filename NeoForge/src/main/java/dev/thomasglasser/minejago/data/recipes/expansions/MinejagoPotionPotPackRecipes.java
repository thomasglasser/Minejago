package dev.thomasglasser.minejago.data.recipes.expansions;

import dev.thomasglasser.minejago.data.recipes.SimpleBrewingRecipeBuilder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.BrewedPotionTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class MinejagoPotionPotPackRecipes extends RecipeProvider
{
    public MinejagoPotionPotPackRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput writer) {
        buildBrewing(writer);
    }

    private void buildBrewing(RecipeOutput writer) {
        List<ItemStack> mundaneItems = new ArrayList<>();
        PotionBrewing.POTION_MIXES.forEach(potionMix ->
        {
            if (potionMix.to == Potions.MUNDANE)
            {
                mundaneItems.addAll(List.of(potionMix.ingredient.getItems()));
            }
            else
                vanillaTea(writer, potionMix);
        });
        vanillaTea(writer, new PotionBrewing.Mix<>(Potions.WATER, Ingredient.of(mundaneItems.toArray(new ItemStack[] {})), Potions.MUNDANE));
    }

    private void vanillaTea(RecipeOutput writer, PotionBrewing.Mix<Potion> mix)
    {
        SimpleBrewingRecipeBuilder.generic(
                        RecipeCategory.BREWING,
                        mix.from,
                        mix.ingredient,
                        mix.to,
                        0.35f,
                        ConstantInt.of(PotionBrewing.BREWING_TIME_SECONDS * 20 * 2))
                .group("potion")
                .unlockedBy("has_brewed_" + BuiltInRegistries.POTION.getKey(mix.to).getPath() + "_in_stand", CriteriaTriggers.BREWED_POTION.createCriterion(new BrewedPotionTrigger.TriggerInstance(Optional.empty(), Optional.of(mix.to.builtInRegistryHolder()))))
                .save(writer);
    }

    @Override
    public @NotNull String getName() {
        return "Minejago Potion Pot Pack " + super.getName();
    }
}
