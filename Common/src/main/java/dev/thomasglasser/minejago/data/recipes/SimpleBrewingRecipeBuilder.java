package dev.thomasglasser.minejago.data.recipes;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class SimpleBrewingRecipeBuilder implements RecipeBuilder
{
    private final RecipeCategory category;
    private final Potion result;
    private final Potion base;
    private final Ingredient ingredient;
    private final float experience;
    private final IntProvider brewingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;
    private TeapotBrewingRecipe.Factory<?> factory;

    private SimpleBrewingRecipeBuilder(RecipeCategory recipeCategory, Potion base, Ingredient ingredient, Potion result, float xp, IntProvider i, TeapotBrewingRecipe.Factory<?> factory) {
        this.category = recipeCategory;
        this.base = base;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = xp;
        this.brewingTime = i;
        this.factory = factory;
    }

    public static <T extends TeapotBrewingRecipe> SimpleBrewingRecipeBuilder generic(RecipeCategory recipeCategory,
                                                                                     Potion base,
                                                                                     Ingredient ingredient,
                                                                                     Potion result,
                                                                                     float xp,
                                                                                     IntProvider i) {
        return new SimpleBrewingRecipeBuilder(recipeCategory, base, ingredient, result, xp, i, TeapotBrewingRecipe::new);
    }

    @Override
    public SimpleBrewingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public SimpleBrewingRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return MinejagoItems.TEACUP.get(); // Does nothing, overridden
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id)
    {
        this.ensureValid(id);
        Advancement.Builder builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        Recipe<?> abstractCookingRecipe = this.factory.create(Objects.requireNonNullElse(this.group, ""), base, ingredient, result, this.experience, this.brewingTime);
        recipeOutput.accept(id, abstractCookingRecipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    @Override
    public void save(RecipeOutput recipeOutput)
    {
        this.save(recipeOutput, getDefaultRecipeId(base, result));
    }

    static ResourceLocation getDefaultRecipeId(Potion from, Potion to) {
        ResourceLocation toLoc = BuiltInRegistries.POTION.getKey(to);
        return new ResourceLocation(toLoc.getNamespace(), BuiltInRegistries.POTION.getKey(from).getPath() + "_to_" + toLoc.getPath());
    }

    /**
     * Makes sure that this obtainable.
     */
    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
