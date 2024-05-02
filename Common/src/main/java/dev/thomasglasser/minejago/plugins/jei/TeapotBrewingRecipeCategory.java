package dev.thomasglasser.minejago.plugins.jei;

import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.common.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.crafting.RecipeHolder;

import static mezz.jei.api.recipe.RecipeIngredientRole.INPUT;
import static mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT;

public class TeapotBrewingRecipeCategory implements IRecipeCategory<RecipeHolder<TeapotBrewingRecipe>>
{
	public static final Component TITLE = Component.translatable(MinejagoRecipeTypes.TEAPOT_BREWING.getId().toLanguageKey("recipe_type"));

	private final IDrawable background;
	private final IDrawable icon;
	private final IDrawableAnimated animatedFlame;

	private RecipeType<RecipeHolder<TeapotBrewingRecipe>> type;

	public TeapotBrewingRecipeCategory(IGuiHelper guiHelper)
	{
		background = guiHelper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 0, 186, 82, 34)
				.addPadding(0, 10, 0, 0)
				.build();
		icon = guiHelper.createDrawableItemStack(MinejagoBlocks.TEAPOT.get().asItem().getDefaultInstance());
		IDrawableStatic staticFlame = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
	}

	@Override
	public void draw(RecipeHolder<TeapotBrewingRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		animatedFlame.draw(guiGraphics, 1, 20);
//		arrow.draw(guiGraphics, 24, 8);
		drawCookTime(recipe, guiGraphics, 35);
	}

	protected void drawCookTime(RecipeHolder<TeapotBrewingRecipe> holder, GuiGraphics guiGraphics, int y) {
		TeapotBrewingRecipe recipe = holder.value();
		IntProvider cookTime = recipe.getCookingTime();
		if (cookTime.getMinValue() > 0) {
			int minSeconds = cookTime.getMinValue() / 20;
			int maxSeconds = cookTime.getMaxValue() / 20;
			Component timeString = Component.translatable("gui.minejago.category.teapot_brewing.time.seconds", minSeconds, maxSeconds);
			Minecraft minecraft = Minecraft.getInstance();
			Font fontRenderer = minecraft.font;
			int stringWidth = fontRenderer.width(timeString);
			guiGraphics.drawString(fontRenderer, timeString, getWidth() - stringWidth, y, 0xFF808080, false);
		}
	}

	@Override
	public RecipeType<RecipeHolder<TeapotBrewingRecipe>> getRecipeType()
	{
		if (type == null)
			type = RecipeType.createFromVanilla(MinejagoRecipeTypes.TEAPOT_BREWING.get());
		return type;
	}

	@Override
	public Component getTitle()
	{
		return TITLE;
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public IDrawable getIcon()
	{
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<TeapotBrewingRecipe> holder, IFocusGroup focuses)
	{
		TeapotBrewingRecipe recipe = holder.value();
		builder.addSlot(INPUT, 1, 1)
				.addIngredients(recipe.getIngredients().getFirst());

		builder.addSlot(OUTPUT, 61, 19)
				.addItemStack(recipe.getResultItem(null));
	}
}
