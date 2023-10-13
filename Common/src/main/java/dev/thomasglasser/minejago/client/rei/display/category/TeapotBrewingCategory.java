package dev.thomasglasser.minejago.client.rei.display.category;

import com.google.common.collect.Lists;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.rei.display.TeapotBrewingDisplay;
import dev.thomasglasser.minejago.util.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.text.DecimalFormat;
import java.util.List;

public class TeapotBrewingCategory implements DisplayCategory<TeapotBrewingDisplay>
{
	public static final CategoryIdentifier<TeapotBrewingDisplay> TEAPOT_BREWING = CategoryIdentifier.of(Minejago.MOD_ID, "plugins/teapot_brewing");
	public static final String CATEGORY_KEY = "category.minejago.brewing";

	@Override
	public CategoryIdentifier<? extends TeapotBrewingDisplay> getCategoryIdentifier()
	{
		return TEAPOT_BREWING;
	}

	@Override
	public Component getTitle()
	{
		return Component.translatable(CATEGORY_KEY);
	}

	@Override
	public Renderer getIcon()
	{
		return EntryStacks.of(MinejagoBlocks.TEAPOT.asItem());
	}

	@Override
	public List<Widget> setupDisplay(TeapotBrewingDisplay display, Rectangle bounds) {
		Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 18);
		List<Widget> widgets = Lists.newArrayList();
		widgets.add(Widgets.createRecipeBase(bounds));
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 30, startPoint.y + 4)));
		widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 71, startPoint.y + 5)));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 71, startPoint.y + 5))
				.entries(display.getOutputEntries().get(0))
				.disableBackground()
				.markOutput());
		DecimalFormat df = new DecimalFormat("###.##");
		widgets.add(Widgets.createLabel(new Point(startPoint.x + 42, startPoint.y + 28),
				Component.translatable("category.rei.cooking.time&xp", df.format(display.getExperience()), display.getCookingTime().getMinValue() == display.getCookingTime().getMaxValue() ? df.format(display.getCookingTime().getMaxValue() / 20) : (display.getCookingTime().getMinValue() / 20) + " - " + (display.getCookingTime().getMaxValue() / 20))).noShadow().color(0xFF404040, 0xFFBBBBBB));
		widgets.add(Widgets.createSlot(new Point(startPoint.x - 18, startPoint.y + 5))
				.entry(EntryStacks.of(MinejagoItemUtils.fillTeacup(display.getBase()))));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 6, startPoint.y + 5))
				.entries(display.getInputEntries().get(0)).markInput());
		return widgets;
	}

	@Override
	public int getDisplayHeight() {
		return 50;
	}
}