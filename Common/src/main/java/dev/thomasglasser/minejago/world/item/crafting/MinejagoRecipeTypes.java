package dev.thomasglasser.minejago.world.item.crafting;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class MinejagoRecipeTypes
{
    public static final RegistrationProvider<RecipeType<?>> RECIPE_TYPES = RegistrationProvider.get(BuiltInRegistries.RECIPE_TYPE, Minejago.MOD_ID);

    public static final Supplier<RecipeType<TeapotBrewingRecipe>> TEAPOT_BREWING = register("teapot_brewing");

    private static <T extends Recipe<?>> Supplier<RecipeType<T>> register(String name)
    {
        return RECIPE_TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }

    public static void init() {}
}
