package dev.thomasglasser.minejago.world.item.crafting.display;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

public class MinejagoRecipeDisplays {
    public static final DeferredRegister<RecipeDisplay.Type<?>> RECIPE_DISPLAYS = DeferredRegister.create(Registries.RECIPE_DISPLAY, Minejago.MOD_ID);

    public static final DeferredHolder<RecipeDisplay.Type<?>, RecipeDisplay.Type<TeapotRecipeDisplay>> TEAPOT = RECIPE_DISPLAYS.register("teapot", () -> TeapotRecipeDisplay.TYPE);

    public static void init() {}
}
