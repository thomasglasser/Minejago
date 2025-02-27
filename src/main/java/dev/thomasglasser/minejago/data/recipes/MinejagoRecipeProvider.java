package dev.thomasglasser.minejago.data.recipes;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.data.recipes.ExtendedRecipeProvider;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.tags.ConventionalItemTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public class MinejagoRecipeProvider extends ExtendedRecipeProvider {
    public MinejagoRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider holderLookup) {
        buildCrafting(recipeOutput);
        buildBrewing(recipeOutput, holderLookup);
    }

    private void buildCrafting(RecipeOutput output) {
        coloredTeacupFromColoredTerracotta(output, MinejagoItems.TEACUP.get(), Blocks.TERRACOTTA);
        MinejagoItems.TEACUPS.forEach((color, cup) -> coloredTeacupFromColoredTerracotta(output, cup.get(), BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(color.getName() + "_terracotta"))));
        teacupCopyRecipes(output);
        shapeless(RecipeCategory.BREWING, MinejagoItems.MINICUP.get(), 1)
                .requires(MinejagoBlocks.TEAPOTS.get(DyeColor.BLACK).get())
                .requires(ItemTags.FISHES)
                .group("teapot")
                .unlockedBy("has_self", has(MinejagoItems.MINICUP.get()))
                .save(output);

        shaped(RecipeCategory.COMBAT, MinejagoItems.BAMBOO_STAFF.get(), 1)
                .pattern(" o ")
                .pattern(" o ")
                .pattern(" o ")
                .define('o', Items.BAMBOO)
                .unlockedBy("has_bamboo", has(Items.BAMBOO))
                .save(output);

        coloredTeapotFromColoredTerracotta(output, MinejagoBlocks.TEAPOT.get(), Blocks.TERRACOTTA);
        MinejagoBlocks.TEAPOTS.forEach((color, pot) -> coloredTeapotFromColoredTerracotta(output, pot.get(), BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(color.getName() + "_terracotta"))));
        teapotCopyRecipes(output);
        shapeless(RecipeCategory.BREWING, MinejagoBlocks.JASPOT.get(), 1)
                .requires(MinejagoBlocks.TEAPOTS.get(DyeColor.CYAN).get())
                .requires(ItemTags.FISHES)
                .group("teapot")
                .unlockedBy("has_self", has(MinejagoBlocks.JASPOT.get()))
                .save(output);

        shapeless(RecipeCategory.MISC, MinejagoItems.SCROLL.get(), 1)
                .requires(Items.PAPER, 3)
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(output);
        shapeless(RecipeCategory.MISC, MinejagoItems.WRITABLE_SCROLL.get(), 1)
                .requires(MinejagoItems.SCROLL.get())
                .requires(Items.FEATHER)
                .requires(Items.INK_SAC)
                .unlockedBy("has_scroll", has(MinejagoItems.SCROLL.get()))
                .save(output);

        shaped(RecipeCategory.REDSTONE, MinejagoBlocks.SCROLL_SHELF.get(), 1)
                .pattern("ppp")
                .pattern("sss")
                .pattern("ppp")
                .define('p', ItemTags.PLANKS)
                .define('s', MinejagoItems.SCROLL.get())
                .unlockedBy("has_scroll", has(MinejagoItems.SCROLL.get()))
                .save(output);

        shaped(RecipeCategory.REDSTONE, MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), 1)
                .pattern("psp")
                .pattern("s s")
                .pattern("psp")
                .define('p', ItemTags.PLANKS)
                .define('s', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_slab", has(ItemTags.WOODEN_SLABS))
                .save(output);

        woodSet(output, MinejagoBlocks.ENCHANTED_WOOD_SET);
    }

    protected void coloredTeapotFromColoredTerracotta(RecipeOutput output, ItemLike pot, ItemLike color) {
        shaped(RecipeCategory.BREWING, pot, 1)
                .pattern("x")
                .pattern("o")
                .define('x', ConventionalItemTags.WOODEN_RODS)
                .define('o', color)
                .group("teapot")
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
                .unlockedBy("has_teapot", has(MinejagoItemTags.TEAPOTS))
                .save(output);
    }

    private void teapotCopyRecipes(RecipeOutput output) {
        for (DyeColor dyecolor : DyeColor.values()) {
            TagKey<Item> dyeItems = ConventionalItemTags.forDyeColor(dyecolor);
//            TransmuteRecipeBuilder.transmute(RecipeCategory.BREWING, this.tag(MinejagoItemTags.TEAPOTS), Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(dyeItems)), MinejagoBlocks.TEAPOTS.get(dyecolor).asItem())
//                    .group("teapot_dye")
//                    .unlockedBy("has_dye", has(dyeItems))
//                    .unlockedBy("has_teapot", has(MinejagoItemTags.TEAPOTS))
//                    .save(output, Minejago.modLoc(dyecolor.getName() + "_teapot_from_dye").toString());
        }
    }

    protected void coloredTeacupFromColoredTerracotta(RecipeOutput output, ItemLike teacup, ItemLike color) {
        shaped(RecipeCategory.BREWING, teacup, 4)
                .pattern("x x")
                .pattern(" x ")
                .define('x', color)
                .group("teacup")
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
                .unlockedBy("has_teacup", has(MinejagoItemTags.TEACUPS))
                .save(output);
    }

    protected void teacupCopyRecipes(RecipeOutput recipeOutput) {
        for (DyeColor color : DyeColor.values()) {
            TagKey<Item> dyeItems = ConventionalItemTags.forDyeColor(color);
            shapeless(RecipeCategory.BREWING, MinejagoItems.TEACUPS.get(color).get(), 1)
                    .requires(MinejagoItemTags.TEACUPS)
                    .requires(dyeItems)
                    .unlockedBy("has_dye", has(dyeItems))
                    .unlockedBy("has_teacup", has(MinejagoItemTags.TEACUPS))
                    .save(recipeOutput, Minejago.modLoc(color.getName() + "_teacup_from_dye"));
        }
    }

    private void buildBrewing(RecipeOutput output, HolderLookup.Provider lookupProvider) {
        normalTea(output, lookupProvider, Items.ACACIA_LEAVES, MinejagoPotions.ACACIA_TEA);
        normalTea(output, lookupProvider, Items.OAK_LEAVES, MinejagoPotions.OAK_TEA);
        normalTea(output, lookupProvider, Items.CHERRY_LEAVES, MinejagoPotions.CHERRY_TEA);
        normalTea(output, lookupProvider, Items.SPRUCE_LEAVES, MinejagoPotions.SPRUCE_TEA);
        normalTea(output, lookupProvider, Items.MANGROVE_LEAVES, MinejagoPotions.MANGROVE_TEA);
        normalTea(output, lookupProvider, Items.JUNGLE_LEAVES, MinejagoPotions.JUNGLE_TEA);
        normalTea(output, lookupProvider, Items.DARK_OAK_LEAVES, MinejagoPotions.DARK_OAK_TEA);
        normalTea(output, lookupProvider, Items.BIRCH_LEAVES, MinejagoPotions.BIRCH_TEA);
        normalTea(output, lookupProvider, Items.AZALEA_LEAVES, MinejagoPotions.AZALEA_TEA);
        normalTea(output, lookupProvider, Items.FLOWERING_AZALEA_LEAVES, MinejagoPotions.FLOWERING_AZALEA_TEA);
        normalTea(output, lookupProvider, MinejagoBlocks.FOCUS_LEAVES_SET.leaves().asItem(), MinejagoPotions.FOCUS_TEA);
    }

    private void normalTea(RecipeOutput output, HolderLookup.Provider registries, Item ingredient, DeferredHolder<Potion, Potion> result) {
        normalTea(output, ingredient, result.asReferenceFrom(registries));
    }

    private void normalTea(RecipeOutput output, Item ingredient, Holder<Potion> result) {
        SimpleBrewingRecipeBuilder.generic(
                RecipeCategory.BREWING,
                Potions.WATER,
                Ingredient.of(ingredient),
                result,
                0.5f,
                UniformInt.of(1200, 2400))
                .group(result.unwrapKey().orElseThrow().location().getPath())
                .unlockedBy("has_ingredient", has(ingredient))
                .save(output);
    }
}
