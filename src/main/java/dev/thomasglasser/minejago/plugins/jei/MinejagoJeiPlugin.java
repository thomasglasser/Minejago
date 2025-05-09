package dev.thomasglasser.minejago.plugins.jei;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.FilledTeacupItem;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.library.plugins.vanilla.ingredients.subtypes.PotionSubtypeInterpreter;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

@JeiPlugin
public class MinejagoJeiPlugin implements IModPlugin {
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        MinejagoBlocks.allPots().forEach(block -> registration.addRecipeCatalyst(block.asItem().getDefaultInstance(), TeapotBrewingRecipeCategory.RECIPE_TYPE));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().getConnection().getRecipeManager();

        registration.addRecipes(TeapotBrewingRecipeCategory.RECIPE_TYPE, compileTeapotBrewingRecipes(recipeManager));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                new TeapotBrewingRecipeCategory(guiHelper));
    }

    private List<TeapotBrewingRecipe> compileTeapotBrewingRecipes(RecipeManager recipeManager) {
        return recipeManager.getAllRecipesFor(MinejagoRecipeTypes.TEAPOT_BREWING.get()).stream().map(RecipeHolder::value).toList();
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        for (FilledTeacupItem teacup : MinejagoItems.allFilledTeacups()) {
            registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, teacup, PotionSubtypeInterpreter.INSTANCE);
        }
        MinejagoArmors.ELEMENTAL_GI_SETS.forEach(set -> set.getAllAsItems().forEach(item -> registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, item, GiSubtypeInterpreter.INSTANCE)));
        MinejagoArmors.SPECIAL_ELEMENTAL_GI_SETS.forEach(set -> set.getAllAsItems().forEach(item -> registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, item, GiSubtypeInterpreter.INSTANCE)));
        MinejagoArmors.STANDALONE_ELEMENTAL_GI.forEach(item -> registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, item.get(), GiSubtypeInterpreter.INSTANCE));
        MinejagoArmors.STANDALONE_SPECIAL_ELEMENTAL_GI.forEach(item -> registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, item.get(), GiSubtypeInterpreter.INSTANCE));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return Minejago.modLoc("jei");
    }
}
