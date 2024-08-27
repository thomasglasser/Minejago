package dev.thomasglasser.minejago.plugins.jei;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.IRecipeWidget;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.common.Constants;
import mezz.jei.library.plugins.vanilla.cooking.FurnaceVariantCategory;
import mezz.jei.library.util.RecipeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class TeapotBrewingRecipeCategory extends FurnaceVariantCategory<TeapotBrewingRecipe> {
    public static final RecipeType<TeapotBrewingRecipe> RECIPE_TYPE = RecipeType.create(MinejagoRecipeTypes.TEAPOT_BREWING.getId().getNamespace(), MinejagoRecipeTypes.TEAPOT_BREWING.getId().getPath(), TeapotBrewingRecipe.class);
    public static final String RECIPE_KEY = MinejagoRecipeTypes.TEAPOT_BREWING.getId().toLanguageKey("recipe");

    private final IGuiHelper guiHelper;
    private final Component title = Component.translatable(RECIPE_KEY);
    private final IDrawable background;
    private final IDrawable icon;

    public TeapotBrewingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.guiHelper = guiHelper;
        this.background = guiHelper.createDrawable(Minejago.modLoc(""), 26, 22, 125, 18);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, MinejagoBlocks.TEAPOT.toStack());
    }

    @Override
    public void draw(TeapotBrewingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        animatedFlame.draw(guiGraphics, 1, 20);

        drawExperience(recipe, guiGraphics, 0);
        drawCookTime(recipe, guiGraphics, 45);
    }

    protected void drawExperience(TeapotBrewingRecipe recipe, GuiGraphics guiGraphics, int y) {
        float experience = recipe.experience();
        if (experience > 0) {
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(experienceString);
            guiGraphics.drawString(fontRenderer, experienceString, getWidth() - stringWidth, y, 0xFF808080, false);
        }
    }

    protected void drawCookTime(TeapotBrewingRecipe recipe, GuiGraphics guiGraphics, int y) {
        IntProvider time = recipe.brewingTime();
        String value;
        if (time instanceof ConstantInt constantInt) {
            value = String.valueOf(constantInt.getValue() / 20);
        } else {
            int minTimeSeconds = time.getMinValue() / 20;
            int maxTimeSeconds = time.getMaxValue() / 20;
            value = minTimeSeconds + "-" + maxTimeSeconds;
        }
        Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", value);
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int stringWidth = fontRenderer.width(timeString);
        guiGraphics.drawString(fontRenderer, timeString, getWidth() - stringWidth, y, 0xFF808080, false);
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder acceptor, TeapotBrewingRecipe recipe, IFocusGroup focuses) {
        acceptor.addWidget(createCookingArrowWidget(recipe, new ScreenPosition(24, 18)));
    }

    protected IRecipeWidget createCookingArrowWidget(TeapotBrewingRecipe recipe, ScreenPosition position) {
        return new CookingArrowRecipeWidget<>(guiHelper, recipe, position);
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
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TeapotBrewingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addIngredients(Ingredient.of(MinejagoItemUtils.fillTeacup(recipe.base())));
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 1)
                .addIngredients(recipe.ingredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1)
                .addItemStack(RecipeUtil.getResultItem(recipe));
    }

    private static class CookingArrowRecipeWidget<T extends AbstractCookingRecipe> implements IRecipeWidget {
        private final IDrawableAnimated arrow;
        private final ScreenPosition position;

        public CookingArrowRecipeWidget(IGuiHelper guiHelper, TeapotBrewingRecipe recipe, ScreenPosition position) {
            int time = (recipe.brewingTime().getMinValue() + recipe.brewingTime().getMaxValue()) / 2;
            this.arrow = guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17)
                    .buildAnimated(time, IDrawableAnimated.StartDirection.LEFT, false);
            this.position = position;
        }

        @Override
        public ScreenPosition getPosition() {
            return position;
        }

        @Override
        public void draw(GuiGraphics guiGraphics, double mouseX, double mouseY) {
            arrow.draw(guiGraphics);
        }
    }
}
