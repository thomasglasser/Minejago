package dev.thomasglasser.minejago.world.item.crafting;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MinejagoRecipeSerializers
{
    public static final RegistrationProvider<RecipeSerializer<?>> RECIPE_SERIALIZERS = RegistrationProvider.get(BuiltInRegistries.RECIPE_SERIALIZER, Minejago.MOD_ID);

    public static final RegistryObject<RecipeSerializer<TeapotBrewingRecipe>> TEAPOT_BREWING_RECIPE = RECIPE_SERIALIZERS.register("teapot_brewing", () -> new SimpleBrewingSerializer<>(TeapotBrewingRecipe::new));

    public static void init() {}
}
