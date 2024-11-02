package dev.thomasglasser.minejago.data.recipes.expansions;

import dev.thomasglasser.minejago.data.recipes.SimpleBrewingRecipeBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.BrewedPotionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

public class MinejagoPotionPotPackRecipes extends RecipeProvider {
    public MinejagoPotionPotPackRecipes(HolderLookup.Provider p_360573_, RecipeOutput p_360872_) {
        super(p_360573_, p_360872_);
    }

    @Override
    protected void buildRecipes() {
        buildBrewing();
    }

    private void buildBrewing() {
        List<Holder<Item>> mundaneItems = new ArrayList<>();
        PotionBrewing potionBrewing = PotionBrewing.bootstrap(FeatureFlags.VANILLA_SET);
        potionBrewing.potionMixes.forEach(potionMix -> {
            if (potionMix.to() == Potions.MUNDANE) {
                mundaneItems.addAll(potionMix.ingredient().items());
            } else
                vanillaTea(potionMix);
        });
        vanillaTea(new PotionBrewing.Mix<>(Potions.WATER, Ingredient.of(mundaneItems.stream().map(Holder::value)), Potions.MUNDANE));
    }

    private void vanillaTea(PotionBrewing.Mix<Potion> mix) {
        SimpleBrewingRecipeBuilder.generic(
                RecipeCategory.BREWING,
                mix.from(),
                mix.ingredient(),
                mix.to(),
                0.35f,
                ConstantInt.of(PotionBrewing.BREWING_TIME_SECONDS * 20 * 2))
                .group("potion")
                .unlockedBy("has_brewed_" + BuiltInRegistries.POTION.getKey(mix.to().value()).getPath() + "_in_stand", CriteriaTriggers.BREWED_POTION.createCriterion(new BrewedPotionTrigger.TriggerInstance(Optional.empty(), Optional.of(mix.to()))))
                .save(output);
    }
}
