package dev.thomasglasser.minejago.world.item.crafting;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

public class MinejagoRecipeSerializers
{
    public static final RegistrationProvider<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistrationProvider.get(BuiltInRegistries.RECIPE_SERIALIZER, Minejago.MOD_ID);

    public static final RegistryObject<RecipeSerializer<TeapotBrewingRecipe>> TEAPOT_BREWING_RECIPE = register("teapot_brewing", () -> new SimpleBrewingSerializer<>(TeapotBrewingRecipe::new, ConstantInt.of(1200)));

    private static <T extends RecipeSerializer<?>> RegistryObject<T> register(String name, Supplier<T> supplier)
    {
        return RECIPE_SERIALIZERS.register(name, supplier);
    }

    public static void init() {}
}
