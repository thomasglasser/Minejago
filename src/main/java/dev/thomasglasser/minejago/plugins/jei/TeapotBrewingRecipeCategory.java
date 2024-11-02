package dev.thomasglasser.minejago.plugins.jei;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.library.util.RecipeUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import org.jetbrains.annotations.Nullable;

public class TeapotBrewingRecipeCategory implements IRecipeCategory<TeapotBrewingRecipe> {
    public static final RecipeType<TeapotBrewingRecipe> RECIPE_TYPE = RecipeType.create(MinejagoRecipeTypes.TEAPOT_BREWING.getId().getNamespace(), MinejagoRecipeTypes.TEAPOT_BREWING.getId().getPath(), TeapotBrewingRecipe.class);
    public static final String RECIPE_KEY = MinejagoRecipeTypes.TEAPOT_BREWING.getId().toLanguageKey("recipe");

    private static final ResourceLocation TEXTURE = Minejago.modLoc("textures/gui/jei/teapot_brewing.png");

    private final Component title = Component.translatable(RECIPE_KEY);
    private final IDrawable icon;
    private final IDrawable background;

    public TeapotBrewingRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, MinejagoBlocks.TEAPOT.toStack());
        this.background = guiHelper
                .drawableBuilder(TEXTURE, 0, 0, 137, 87)
                .setTextureSize(137, 87)
                .build();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TeapotBrewingRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(7, 41)
                .setStandardSlotBackground()
                .addIngredients(DataComponentIngredient.of(false, MinejagoItemUtils.fillTeacup(recipe.base())));
        builder.addInputSlot(39, 7)
                .setStandardSlotBackground()
                .addIngredients(recipe.ingredient());
        builder.addOutputSlot(108, 41)
                .setOutputSlotBackground()
                .addItemStack(RecipeUtil.getResultItem(recipe));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, TeapotBrewingRecipe recipe, IFocusGroup focuses) {
        int brewTime = (recipe.brewingTime().getMinValue() + recipe.brewingTime().getMaxValue()) / 2;
        builder.addAnimatedRecipeArrow(brewTime)
                .setPosition(72, 40);
        builder.addAnimatedRecipeFlame(300)
                .setPosition(39, 63);

        addExperience(builder, recipe);
        addCookTime(builder, recipe);
    }

    @Override
    public void draw(TeapotBrewingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
    }

    protected void addExperience(IRecipeExtrasBuilder builder, TeapotBrewingRecipe recipe) {
        float experience = recipe.experience();
        if (experience > 0) {
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            builder.addText(experienceString, getWidth() - 20, 10)
                    .setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.TOP)
                    .setTextAlignment(HorizontalAlignment.RIGHT)
                    .setColor(0xFF808080);
        }
    }

    protected void addCookTime(IRecipeExtrasBuilder builder, TeapotBrewingRecipe recipe) {
        IntProvider brewTime = recipe.brewingTime();
        String value;
        if (brewTime instanceof ConstantInt constantInt) {
            value = String.valueOf(constantInt.getValue() / 20);
        } else {
            int minTimeSeconds = brewTime.getMinValue() / 20;
            int maxTimeSeconds = brewTime.getMaxValue() / 20;
            value = minTimeSeconds + "-" + maxTimeSeconds;
        }
        Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", value);
        builder.addText(timeString, getWidth() - 20, 10)
                .setPosition(0, 0, getWidth(), getHeight(), HorizontalAlignment.RIGHT, VerticalAlignment.BOTTOM)
                .setTextAlignment(HorizontalAlignment.RIGHT)
                .setTextAlignment(VerticalAlignment.BOTTOM)
                .setColor(0xFF808080);
    }

    @Override
    public RecipeType<TeapotBrewingRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return 137;
    }

    @Override
    public int getHeight() {
        return 87;
    }
}
