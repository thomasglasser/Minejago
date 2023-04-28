package dev.thomasglasser.minejago.data.recipes;

import dev.thomasglasser.minejago.data.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class MinejagoRecipes extends RecipeProvider {
    public MinejagoRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        buildCrafting(writer);
        buildSmithing(writer);
        buildBrewing(writer);
    }

    private void buildCrafting(Consumer<FinishedRecipe> writer)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, MinejagoItems.TEACUP.get(), 4)
                .pattern("x x")
                .pattern(" x ")
                .define('x', ItemTags.TERRACOTTA)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MinejagoItems.IRON_SPEAR.get(), 1)
                .pattern("o  ")
                .pattern(" x ")
                .pattern("  x")
                .define('x', MinejagoItemTags.WOODEN_RODS)
                .define('o', MinejagoItemTags.IRON_INGOTS)
                .unlockedBy("has_iron", has(MinejagoItemTags.IRON_INGOTS))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MinejagoItems.IRON_KATANA.get(), 1)
                .pattern("  x")
                .pattern(" x ")
                .pattern("o  ")
                .define('x', MinejagoItemTags.IRON_INGOTS)
                .define('o', MinejagoItemTags.WOODEN_RODS)
                .unlockedBy("has_iron", has(MinejagoItemTags.IRON_INGOTS))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MinejagoItems.IRON_SCYTHE.get(), 1)
                .pattern(" x ")
                .pattern("xo ")
                .pattern(" o ")
                .define('x', MinejagoItemTags.IRON_INGOTS)
                .define('o', MinejagoItemTags.WOODEN_RODS)
                .unlockedBy("has_iron", has(MinejagoItemTags.IRON_INGOTS))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, MinejagoItems.WOODEN_NUNCHUCKS.get(), 1)
                .requires(Ingredient.of(MinejagoItemTags.WOODEN_RODS), 2)
                .requires(Items.CHAIN)
                .unlockedBy("has_chain", has(Items.CHAIN))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MinejagoItems.BAMBOO_STAFF.get(), 1)
                .pattern(" o ")
                .pattern(" o ")
                .pattern(" o ")
                .define('o', Items.BAMBOO)
                .unlockedBy("has_bamboo", has(Items.BAMBOO))
                .save(writer);

        coloredTeapotFromColoredTerracotta(writer, MinejagoItems.TEAPOT.get(), Blocks.TERRACOTTA);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, MinejagoItems.JASPOT.get(), 1)
                .requires(MinejagoItems.TEAPOTS.get(DyeColor.CYAN).get())
                .requires(ItemTags.FISHES)
                .group("teapot")
                .unlockedBy("has_self", has(MinejagoBlocks.JASPOT.get()))
                .save(writer);

        MinejagoItems.TEAPOTS.forEach((color, pot) ->
        {
            coloredTeapotFromColoredTerracotta(writer, pot.get(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation(color.getName() + "_terracotta")));
            coloredTeapotFromTeapotAndDye(writer, pot.get(), MinejagoItemTags.DYES_TAGS.get(color));
        });
    }

    private void buildSmithing(Consumer<FinishedRecipe> writer)
    {
        smithing(writer, MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get(), Items.IRON_BLOCK);
    }

    private void smithing(Consumer<FinishedRecipe> writer, Item template, Item dup)
    {
        trimSmithing(writer, template);
        copySmithingTemplate(writer, template, dup);
    }

    protected void coloredTeapotFromColoredTerracotta(Consumer<FinishedRecipe> writer, ItemLike pot, ItemLike color)
    {
        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, pot, 1)
                .pattern("x")
                .pattern("o")
                .define('x', MinejagoItemTags.WOODEN_RODS)
                .define('o', color)
                .group("teapot")
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
                .unlockedBy("has_teapot", has(MinejagoItemTags.TEAPOTS))
                .save(writer);
    }

    protected void coloredTeapotFromTeapotAndDye(Consumer<FinishedRecipe> writer, ItemLike pColoredTeapot, TagKey<Item> pDye)
    {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, pColoredTeapot, 1)
                .requires(MinejagoItems.TEAPOT.get())
                .requires(pDye)
                .group("teapot")
                .unlockedBy("has_teapot", has(MinejagoItemTags.TEAPOTS))
                .unlockedBy("has_dye", has(pDye))
                .save(writer, RecipeBuilder.getDefaultRecipeId(pColoredTeapot) + "_from_dye");
    }

    private void buildBrewing(Consumer<FinishedRecipe> writer) {
//        SimpleBrewingRecipeBuilder.of(
//                Ingredient.of(Items.COW_SPAWN_EGG),
//                RecipeCategory.BREWING,
//                MinejagoPotions.MILK.get(),
//                100,
//                1400)
//                .unlockedBy(getHasName(Items.COW_SPAWN_EGG), has(Items.COW_SPAWN_EGG))
//                .save(writer);
    }
}
