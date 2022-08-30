package dev.thomasglasser.minejago.data.recipes;

import dev.thomasglasser.minejago.MinejagoMod;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class MinejagoRecipes extends RecipeProvider {
    public MinejagoRecipes(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder teacup = ShapedRecipeBuilder.shaped(MinejagoItems.TEACUP.get(), 4)
                .pattern("x x")
                .pattern(" x ")
                .define('x', ItemTags.TERRACOTTA)
                .group(MinejagoMod.MODID)
                .unlockedBy("has_terracotta", InventoryChangeTrigger.TriggerInstance.hasItems(Items.TERRACOTTA));
        teacup.save(writer);
    }
}
