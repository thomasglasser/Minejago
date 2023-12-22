package dev.thomasglasser.minejago.world.item.crafting;

import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class MinejagoRecipeTypes
{
    public static final Supplier<RecipeType<TeapotBrewingRecipe>> TEAPOT_BREWING = register("teapot_brewing");

    private static <T extends Recipe<?>> Supplier<RecipeType<T>> register(String name)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.RECIPE_TYPE, name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }

    public static void init() {}
}
