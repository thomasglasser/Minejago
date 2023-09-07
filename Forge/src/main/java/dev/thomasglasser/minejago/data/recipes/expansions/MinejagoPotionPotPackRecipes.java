package dev.thomasglasser.minejago.data.recipes.expansions;

import dev.thomasglasser.minejago.data.recipes.SimpleBrewingRecipeBuilder;
import net.minecraft.advancements.critereon.BrewedPotionTrigger;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MinejagoPotionPotPackRecipes extends RecipeProvider
{
    public MinejagoPotionPotPackRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        buildBrewing(writer);
    }

    private void buildBrewing(Consumer<FinishedRecipe> writer) {
        List<ItemStack> mundaneItems = new ArrayList<>();
        PotionBrewing.POTION_MIXES.forEach(potionMix ->
        {
            if (potionMix.to.is(BuiltInRegistries.POTION.getKey(Potions.MUNDANE)))
            {
                mundaneItems.addAll(List.of(potionMix.ingredient.getItems()));
            }
            else
                vanillaTea(writer, potionMix);
        });
        vanillaTea(writer, new PotionBrewing.Mix<>(ForgeRegistries.POTIONS, Potions.WATER, Ingredient.of(mundaneItems.toArray(new ItemStack[] {})), Potions.MUNDANE));
    }

    private void vanillaTea(Consumer<FinishedRecipe> writer, PotionBrewing.Mix<Potion> mix)
    {
        SimpleBrewingRecipeBuilder.of(
                        mix.from.get(),
                        mix.ingredient,
                        mix.to.get(),
                        0.35f,
                        ConstantInt.of(PotionBrewing.BREWING_TIME_SECONDS * 20 * 2))
                .group("potion")
                .unlockedBy("has_brewed_" + BuiltInRegistries.POTION.getKey(mix.to.get()).getPath() + "_in_stand", new BrewedPotionTrigger.TriggerInstance(ContextAwarePredicate.ANY, mix.to.get()))
                .save(writer);
    }

    @Override
    public @NotNull String getName() {
        return "Minejago Potion Pot Pack " + super.getName();
    }
}
