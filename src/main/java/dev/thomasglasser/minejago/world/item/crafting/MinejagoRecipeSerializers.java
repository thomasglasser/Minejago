package dev.thomasglasser.minejago.world.item.crafting;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MinejagoRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Minejago.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TeapotBrewingRecipe>> TEAPOT_BREWING_RECIPE = register("teapot_brewing", () -> new SimpleBrewingSerializer<>(TeapotBrewingRecipe::new, ConstantInt.of(1200)));

    private static <T extends RecipeSerializer<?>> DeferredHolder<RecipeSerializer<?>, T> register(String name, Supplier<T> supplier) {
        return RECIPE_SERIALIZERS.register(name, supplier);
    }

    public static void init() {}
}
