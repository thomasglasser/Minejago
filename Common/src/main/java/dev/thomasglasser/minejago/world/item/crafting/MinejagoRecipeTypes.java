package dev.thomasglasser.minejago.world.item.crafting;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class MinejagoRecipeTypes
{
    public static final RegistrationProvider<RecipeType<?>> RECIPE_TYPES = RegistrationProvider.get(BuiltInRegistries.RECIPE_TYPE, Minejago.MOD_ID);

    public static final RegistryObject<RecipeType<TeapotBrewingRecipe>> TEAPOT_BREWING = register("teapot_brewing");

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(String name)
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
