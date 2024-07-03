package dev.thomasglasser.minejago.data.recipes;

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
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
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

public class MinejagoRecipes extends ExtendedRecipeProvider {
    public static HolderLookup.Provider lookupProvider = null;

    public MinejagoRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput writer) {
        buildCrafting(writer);
        buildBrewing(writer);
    }

    private void buildCrafting(RecipeOutput writer) {
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
                .define('x', ConventionalItemTags.WOODEN_RODS)
                .define('o', ConventionalItemTags.IRON_INGOTS)
                .unlockedBy("has_iron", has(ConventionalItemTags.IRON_INGOTS))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MinejagoItems.IRON_KATANA.get(), 1)
                .pattern("  x")
                .pattern(" x ")
                .pattern("o  ")
                .define('x', ConventionalItemTags.IRON_INGOTS)
                .define('o', ConventionalItemTags.WOODEN_RODS)
                .unlockedBy("has_iron", has(ConventionalItemTags.IRON_INGOTS))
                .save(writer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MinejagoItems.IRON_SCYTHE.get(), 1)
                .pattern(" x ")
                .pattern("xo ")
                .pattern(" o ")
                .define('x', ConventionalItemTags.IRON_INGOTS)
                .define('o', ConventionalItemTags.WOODEN_RODS)
                .unlockedBy("has_iron", has(ConventionalItemTags.IRON_INGOTS))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, MinejagoItems.WOODEN_NUNCHUCKS.get(), 1)
                .requires(Ingredient.of(ConventionalItemTags.WOODEN_RODS), 2)
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

        coloredTeapotFromColoredTerracotta(writer, MinejagoBlocks.TEAPOT.get(), Blocks.TERRACOTTA);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, MinejagoBlocks.JASPOT.get(), 1)
                .requires(MinejagoBlocks.TEAPOTS.get(DyeColor.CYAN).get())
                .requires(ItemTags.FISHES)
                .group("teapot")
                .unlockedBy("has_self", has(MinejagoBlocks.JASPOT.get()))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, MinejagoBlocks.FLAME_TEAPOT.get(), 1)
                .requires(MinejagoBlocks.TEAPOTS.get(DyeColor.GRAY).get())
                .requires(ConventionalItemTags.ORANGE_DYES)
                .group("teapot")
                .unlockedBy("has_self", has(MinejagoBlocks.FLAME_TEAPOT.get()))
                .unlockedBy("has_orange_dye", has(ConventionalItemTags.ORANGE_DYES))
                .unlockedBy("has_teapot", has(MinejagoItemTags.TEAPOTS))
                .save(writer);

        MinejagoBlocks.TEAPOTS.forEach((color, pot) -> {
            coloredTeapotFromColoredTerracotta(writer, pot.get(), BuiltInRegistries.BLOCK.get(ResourceLocation.withDefaultNamespace(color.getName() + "_terracotta")));
            coloredTeapotFromTeapotAndDye(writer, pot.get(), ConventionalItemTags.forDyeColor(color));
        });

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MinejagoItems.SCROLL.get(), 1)
                .requires(Items.PAPER, 3)
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(writer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MinejagoItems.WRITABLE_SCROLL.get(), 1)
                .requires(MinejagoItems.SCROLL.get())
                .requires(Items.FEATHER)
                .requires(Items.INK_SAC)
                .unlockedBy("has_scroll", has(MinejagoItems.SCROLL.get()))
                .save(writer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), 1)
                .pattern("psp")
                .pattern("s s")
                .pattern("psp")
                .define('p', ItemTags.PLANKS)
                .define('s', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_slab", has(ItemTags.WOODEN_SLABS))
                .save(writer);

        woodSet(writer, MinejagoBlocks.ENCHANTED_WOOD_SET);
    }

    protected void coloredTeapotFromColoredTerracotta(RecipeOutput writer, ItemLike pot, ItemLike color) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, pot, 1)
                .pattern("x")
                .pattern("o")
                .define('x', ConventionalItemTags.WOODEN_RODS)
                .define('o', color)
                .group("teapot")
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
                .unlockedBy("has_teapot", has(MinejagoItemTags.TEAPOTS))
                .save(writer);
    }

    protected void coloredTeapotFromTeapotAndDye(RecipeOutput writer, ItemLike pColoredTeapot, TagKey<Item> pDye) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, pColoredTeapot, 1)
                .requires(MinejagoBlocks.TEAPOT.get())
                .requires(pDye)
                .group("teapot")
                .unlockedBy("has_teapot", has(MinejagoItemTags.TEAPOTS))
                .unlockedBy("has_dye", has(pDye))
                .save(writer, RecipeBuilder.getDefaultRecipeId(pColoredTeapot) + "_from_dye");
    }

    private void buildBrewing(RecipeOutput writer) {
        normalTea(writer, Items.ACACIA_LEAVES, MinejagoPotions.ACACIA_TEA);
        normalTea(writer, Items.OAK_LEAVES, MinejagoPotions.OAK_TEA);
        normalTea(writer, Items.CHERRY_LEAVES, MinejagoPotions.CHERRY_TEA);
        normalTea(writer, Items.SPRUCE_LEAVES, MinejagoPotions.SPRUCE_TEA);
        normalTea(writer, Items.MANGROVE_LEAVES, MinejagoPotions.MANGROVE_TEA);
        normalTea(writer, Items.JUNGLE_LEAVES, MinejagoPotions.JUNGLE_TEA);
        normalTea(writer, Items.DARK_OAK_LEAVES, MinejagoPotions.DARK_OAK_TEA);
        normalTea(writer, Items.BIRCH_LEAVES, MinejagoPotions.BIRCH_TEA);
        normalTea(writer, Items.AZALEA_LEAVES, MinejagoPotions.AZALEA_TEA);
        normalTea(writer, Items.FLOWERING_AZALEA_LEAVES, MinejagoPotions.FLOWERING_AZALEA_TEA);
        normalTea(writer, MinejagoBlocks.FOCUS_LEAVES_SET.leaves().asItem(), MinejagoPotions.FOCUS_TEA);
    }

    private void normalTea(RecipeOutput writer, Item ingredient, DeferredHolder<Potion, Potion> result) {
        normalTea(writer, ingredient, result.asReferenceFrom(lookupProvider));
    }

    private void normalTea(RecipeOutput writer, Item ingredient, Holder<Potion> result) {
        SimpleBrewingRecipeBuilder.generic(
                RecipeCategory.BREWING,
                Potions.WATER,
                Ingredient.of(ingredient),
                result,
                0.5f,
                UniformInt.of(1200, 2400))
                .group(result.unwrapKey().orElseThrow().location().getPath())
                .unlockedBy("has_ingredient", has(ingredient))
                .save(writer);
    }
}
