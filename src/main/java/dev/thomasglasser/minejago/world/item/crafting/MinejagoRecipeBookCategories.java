package dev.thomasglasser.minejago.world.item.crafting;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;

public class MinejagoRecipeBookCategories {
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, Minejago.MOD_ID);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> TEAPOT_BREWING = RECIPE_BOOK_CATEGORIES.register("teapot_brewing", RecipeBookCategory::new);

    public static void init() {}
}
