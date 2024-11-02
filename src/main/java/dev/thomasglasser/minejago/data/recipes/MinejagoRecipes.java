package dev.thomasglasser.minejago.data.recipes;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.data.recipes.ExtendedRecipeProvider;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.tags.ConventionalItemTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.TransmuteRecipeBuilder;
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
    public MinejagoRecipes(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        buildCrafting();
        buildBrewing();
    }

    private void buildCrafting() {
        shaped(RecipeCategory.BREWING, MinejagoItems.TEACUP.get(), 4)
                .pattern("x x")
                .pattern(" x ")
                .define('x', ItemTags.TERRACOTTA)
                .unlockedBy("has_terracotta", has(ItemTags.TERRACOTTA))
                .save(output);
        shaped(RecipeCategory.COMBAT, MinejagoItems.BAMBOO_STAFF.get(), 1)
                .pattern(" o ")
                .pattern(" o ")
                .pattern(" o ")
                .define('o', Items.BAMBOO)
                .unlockedBy("has_bamboo", has(Items.BAMBOO))
                .save(output);

        coloredTeapotFromColoredTerracotta(MinejagoBlocks.TEAPOT.get(), Blocks.TERRACOTTA);
        shapeless(RecipeCategory.BREWING, MinejagoBlocks.JASPOT.get(), 1)
                .requires(MinejagoBlocks.TEAPOTS.get(DyeColor.CYAN).get())
                .requires(ItemTags.FISHES)
                .group("teapot")
                .unlockedBy("has_self", has(MinejagoBlocks.JASPOT.get()))
                .save(output);
        shapeless(RecipeCategory.BREWING, MinejagoBlocks.FLAME_TEAPOT.get(), 1)
                .requires(MinejagoBlocks.TEAPOTS.get(DyeColor.GRAY).get())
                .requires(ConventionalItemTags.ORANGE_DYES)
                .group("teapot")
                .unlockedBy("has_self", has(MinejagoBlocks.FLAME_TEAPOT.get()))
                .unlockedBy("has_orange_dye", has(ConventionalItemTags.ORANGE_DYES))
                .unlockedBy("has_teapot", has(MinejagoItemTags.TEAPOTS))
                .save(output);

        MinejagoBlocks.TEAPOTS.forEach((color, pot) -> coloredTeapotFromColoredTerracotta(pot.get(), BuiltInRegistries.BLOCK.getValue(ResourceLocation.withDefaultNamespace(color.getName() + "_terracotta"))));
        teapotRecipes();
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

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
    }

    protected void coloredTeapotFromColoredTerracotta(ItemLike pot, ItemLike color) {
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

    private void teapotRecipes() {
        for (DyeColor dyecolor : DyeColor.values()) {
            TagKey<Item> dyeItems = ConventionalItemTags.forDyeColor(dyecolor);
            TransmuteRecipeBuilder.transmute(RecipeCategory.BREWING, this.tag(MinejagoItemTags.TEAPOTS), Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(dyeItems)), MinejagoBlocks.TEAPOTS.get(dyecolor).asItem())
                    .group("teapot_dye")
                    .unlockedBy("has_dye", this.has(dyeItems))
                    .save(this.output, Minejago.modLoc(dyecolor.getName() + "_teapot_from_dye").toString());
        }
    }

    private void buildBrewing() {
        normalTea(Items.ACACIA_LEAVES, MinejagoPotions.ACACIA_TEA);
        normalTea(Items.OAK_LEAVES, MinejagoPotions.OAK_TEA);
        normalTea(Items.CHERRY_LEAVES, MinejagoPotions.CHERRY_TEA);
        normalTea(Items.SPRUCE_LEAVES, MinejagoPotions.SPRUCE_TEA);
        normalTea(Items.MANGROVE_LEAVES, MinejagoPotions.MANGROVE_TEA);
        normalTea(Items.JUNGLE_LEAVES, MinejagoPotions.JUNGLE_TEA);
        normalTea(Items.DARK_OAK_LEAVES, MinejagoPotions.DARK_OAK_TEA);
        normalTea(Items.BIRCH_LEAVES, MinejagoPotions.BIRCH_TEA);
        normalTea(Items.AZALEA_LEAVES, MinejagoPotions.AZALEA_TEA);
        normalTea(Items.FLOWERING_AZALEA_LEAVES, MinejagoPotions.FLOWERING_AZALEA_TEA);
        normalTea(MinejagoBlocks.FOCUS_LEAVES_SET.leaves().asItem(), MinejagoPotions.FOCUS_TEA);
    }

    private void normalTea(Item ingredient, DeferredHolder<Potion, Potion> result) {
        normalTea(ingredient, result.asReferenceFrom(registries));
    }

    private void normalTea(Item ingredient, Holder<Potion> result) {
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
