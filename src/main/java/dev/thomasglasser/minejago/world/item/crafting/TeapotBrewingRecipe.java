package dev.thomasglasser.minejago.world.item.crafting;

import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class TeapotBrewingRecipe implements Recipe<TeapotBrewingRecipe.TeapotBrewingRecipeInput> {
    protected final String group;
    protected final Holder<Potion> base;
    protected final Ingredient ingredient;
    protected final Holder<Potion> result;
    protected final float experience;
    protected final IntProvider cookingTime;

    public TeapotBrewingRecipe(
            String string,
            Holder<Potion> base,
            Ingredient ingredient,
            Holder<Potion> result,
            float xp,
            IntProvider i) {
        this.group = string;
        this.base = base;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = xp;
        this.cookingTime = i;
    }

    @Override
    public boolean matches(TeapotBrewingRecipeInput input, Level p_345375_) {
        return ingredient.test(input.ingredient()) && base == input.base();
    }

    @Override
    public ItemStack assemble(TeapotBrewingRecipeInput input, HolderLookup.Provider provider) {
        return getResultItem(provider);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    /**
     * Gets the experience of this recipe
     */
    public float getExperience() {
        return this.experience;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return MinejagoItemUtils.fillTeacup(result);
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    /**
     * Gets the cook time in ticks
     */
    public IntProvider getCookingTime() {
        return this.cookingTime;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MinejagoRecipeSerializers.TEAPOT_BREWING_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return MinejagoRecipeTypes.TEAPOT_BREWING.get();
    }

    public CookingBookCategory category() {
        return CookingBookCategory.FOOD;
    }

    @Override
    public ItemStack getToastSymbol() {
        return MinejagoBlocks.TEAPOT.get().asItem().getDefaultInstance();
    }

    public Holder<Potion> getBase() {
        return base;
    }

    public interface Factory<T extends TeapotBrewingRecipe> {
        T create(String group,
                Holder<Potion> base,
                Ingredient ingredient,
                Holder<Potion> result,
                float xp,
                IntProvider i);
    }

    public record TeapotBrewingRecipeInput(Holder<Potion> base, ItemStack ingredient) implements RecipeInput {
        @Override
        public ItemStack getItem(int p_346128_) {
            return switch (p_346128_) {
                case 0 -> MinejagoItemUtils.fillTeacup(base);
                case 1 -> ingredient;
                default -> ItemStack.EMPTY;
            };
        }

        @Override
        public int size() {
            return 2;
        }
    }
}
