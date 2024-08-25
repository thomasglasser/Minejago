package dev.thomasglasser.minejago.plugins.jei;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.library.plugins.vanilla.ingredients.subtypes.PotionSubtypeInterpreter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

@JeiPlugin
public class MinejagoJeiPlugin implements IModPlugin {
    @Nullable
    private TeapotBrewingRecipeCategory teapotBrewingRecipeCategory;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                teapotBrewingRecipeCategory = new TeapotBrewingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (teapotBrewingRecipeCategory != null)
            registration.addRecipes(teapotBrewingRecipeCategory.getRecipeType(), getTeapotBrewingRecipes());
    }

    public List<RecipeHolder<TeapotBrewingRecipe>> getTeapotBrewingRecipes() {
        return ClientUtils.getMinecraft().level.getRecipeManager().getAllRecipesFor(MinejagoRecipeTypes.TEAPOT_BREWING.get());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (teapotBrewingRecipeCategory != null)
            registration.addRecipeCatalyst(MinejagoBlocks.TEAPOT.get().asItem().getDefaultInstance(), teapotBrewingRecipeCategory.getRecipeType());
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, MinejagoItems.FILLED_TEACUP.get(), PotionSubtypeInterpreter.INSTANCE);
        MinejagoArmors.TRAINING_GI_SET.getAll().forEach(item -> registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, item.get(), GiSubtypeInterpreter.INSTANCE));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return Minejago.modLoc("jei");
    }
}
