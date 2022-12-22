package dev.thomasglasser.minejago.data.recipes;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class MinejagoRecipes extends RecipeProvider {
    public MinejagoRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder teacup = ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, MinejagoItems.TEACUP.get(), 4)
                .pattern("x x")
                .pattern(" x ")
                .define('x', ItemTags.TERRACOTTA)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA));
        teacup.save(writer);
        ShapedRecipeBuilder ironSpear = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MinejagoItems.IRON_SPEAR.get(), 1)
                .pattern("o  ")
                .pattern(" x ")
                .pattern("  x")
                .define('x', Tags.Items.RODS_WOODEN)
                .define('o', Tags.Items.INGOTS_IRON)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON));
        ironSpear.save(writer);
        ShapedRecipeBuilder teapot = ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, MinejagoItems.TEAPOT.get(), 1)
                .pattern("x  ")
                .pattern("o  ")
                .define('x', Tags.Items.RODS_WOODEN)
                .define('o', ItemTags.TERRACOTTA)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA));
        teapot.save(writer);
        ShapedRecipeBuilder ironKatana = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MinejagoItems.IRON_KATANA.get(), 1)
                .pattern("  x")
                .pattern(" x ")
                .pattern("o  ")
                .define('x', Tags.Items.INGOTS_IRON)
                .define('o', Tags.Items.RODS_WOODEN)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON));
        ironKatana.save(writer);
        ShapedRecipeBuilder ironScythe = ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MinejagoItems.IRON_SCYTHE.get(), 1)
                .pattern(" x ")
                .pattern("xo ")
                .pattern(" o ")
                .define('x', Tags.Items.INGOTS_IRON)
                .define('o', Tags.Items.RODS_WOODEN)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON));
        ironScythe.save(writer);
        ShapelessRecipeBuilder woodenNunchucks = ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, MinejagoItems.WOODEN_NUNCHUCKS.get(), 1)
                .requires(Ingredient.of(Tags.Items.RODS_WOODEN), 2)
                .requires(Items.CHAIN)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_chain", has(Items.CHAIN));
        woodenNunchucks.save(writer);
    }
}
