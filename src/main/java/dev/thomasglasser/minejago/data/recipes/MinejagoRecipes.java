package dev.thomasglasser.minejago.data.recipes;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

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
                .group(Minejago.MOD_ID)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA));
        teacup.save(writer);
        ShapedRecipeBuilder ironSpear = ShapedRecipeBuilder.shaped(MinejagoItems.IRON_SPEAR.get(), 1)
                .pattern("o  ")
                .pattern(" x ")
                .pattern("  x")
                .define('x', Tags.Items.RODS_WOODEN)
                .define('o', Tags.Items.INGOTS_IRON)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON));
        ironSpear.save(writer);
        ShapedRecipeBuilder teapot = ShapedRecipeBuilder.shaped(MinejagoItems.TEAPOT.get(), 1)
                .pattern("x  ")
                .pattern("o  ")
                .define('x', Tags.Items.RODS_WOODEN)
                .define('o', ItemTags.TERRACOTTA)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA));
        teapot.save(writer);
        ShapedRecipeBuilder ironKatana = ShapedRecipeBuilder.shaped(MinejagoItems.IRON_KATANA.get(), 1)
                .pattern("  x")
                .pattern(" x ")
                .pattern("o  ")
                .define('x', Tags.Items.INGOTS_IRON)
                .define('o', Tags.Items.RODS_WOODEN)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON));
        ironKatana.save(writer);
        ShapedRecipeBuilder ironScythe = ShapedRecipeBuilder.shaped(MinejagoItems.IRON_SCYTHE.get(), 1)
                .pattern(" x ")
                .pattern("xo ")
                .pattern(" o ")
                .define('x', Tags.Items.INGOTS_IRON)
                .define('o', Tags.Items.RODS_WOODEN)
                .group(Minejago.MOD_ID)
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON));
        ironScythe.save(writer);
    }
}
