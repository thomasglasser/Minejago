package dev.thomasglasser.minejago.data.recipes;

import com.google.gson.JsonObject;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeSerializers;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SimpleBrewingRecipeBuilder implements RecipeBuilder
{
    private final RecipeCategory category;
    private final Potion result;
    private final Ingredient ingredient;
    private final float experience;
    private final int brewingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final RecipeSerializer<? extends TeapotBrewingRecipe> serializer;

    private SimpleBrewingRecipeBuilder(RecipeCategory pCategory, Potion pResult, Ingredient pIngredient, float pExperience, int pCookingTime, RecipeSerializer<? extends TeapotBrewingRecipe> pSerializer) {
        this.category = pCategory;
        this.result = pResult;
        this.ingredient = pIngredient;
        this.experience = pExperience;
        this.brewingTime = pCookingTime;
        this.serializer = pSerializer;
    }

    public static SimpleBrewingRecipeBuilder of(Ingredient pIngredient, RecipeCategory pCategory, Potion pResult, float pExperience, int brewingTime) {
        return new SimpleBrewingRecipeBuilder(pCategory, pResult, pIngredient, pExperience, brewingTime, MinejagoRecipeSerializers.TEAPOT_BREWING_RECIPE.get());
    }

    public SimpleBrewingRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    public SimpleBrewingRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    public Item getResult() {
        return null;
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new SimpleBrewingRecipeBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.ingredient, this.result, this.experience, this.brewingTime, this.advancement, pRecipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.serializer));
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        save(pFinishedRecipeConsumer, getDefaultRecipeId(result));
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, String recipeId) {
        ResourceLocation resourceLocation = getDefaultRecipeId(result);
        ResourceLocation resourceLocation2 = new ResourceLocation(recipeId);
        if (resourceLocation2.equals(resourceLocation)) {
            throw new IllegalStateException("Recipe " + recipeId + " should remove its 'save' argument as it is equal to default one");
        } else {
            this.save(finishedRecipeConsumer, resourceLocation2);
        }
    }

    static ResourceLocation getDefaultRecipeId(Potion potion) {
        return BuiltInRegistries.POTION.getKey(potion);
    }

    /**
     * Makes sure that this obtainable.
     */
    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final Potion result;
        private final float experience;
        private final int brewingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends TeapotBrewingRecipe> serializer;

        public Result(ResourceLocation pId, String pGroup, Ingredient pIngredient, Potion pResult, float pExperience, int pCookingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends TeapotBrewingRecipe> pSerializer) {
            this.id = pId;
            this.group = pGroup;
            this.ingredient = pIngredient;
            this.result = pResult;
            this.experience = pExperience;
            this.brewingTime = pCookingTime;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.serializer = pSerializer;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }

            pJson.add("ingredient", this.ingredient.toJson());
            pJson.addProperty("result", BuiltInRegistries.POTION.getKey(this.result).toString());
            pJson.addProperty("experience", this.experience);
            pJson.addProperty("brewingtime", this.brewingTime);
        }

        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        /**
         * Gets the ID for the recipe.
         */
        public ResourceLocation getId() {
            return this.id;
        }

        /**
         * Gets the JSON for the advancement that unlocks this recipe. Null if there is no advancement.
         */
        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        /**
         * Gets the ID for the advancement associated with this recipe. Should not be null if {@link #serializeAdvancement()}
         * is non-null.
         */
        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
