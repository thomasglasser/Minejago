package dev.thomasglasser.minejago.world.item.crafting;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TeapotBrewingRecipe implements Recipe<Container> {
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final Potion result;
    protected final float experience;
    protected final IntProvider cookingTime;

    public TeapotBrewingRecipe(
            ResourceLocation resourceLocation,
            String string,
            Ingredient ingredient,
            Potion potion,
            float f,
            IntProvider i
    ) {
        this.id = resourceLocation;
        this.group = string;
        this.ingredient = ingredient;
        this.result = potion;
        this.experience = f;
        this.cookingTime = i;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return getResultItem(registryAccess);
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
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), result);
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
    public ResourceLocation getId() {
        return this.id;
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
    public @NotNull ItemStack getToastSymbol() {
        return MinejagoBlocks.TEAPOT.get().asItem().getDefaultInstance();
    }
}