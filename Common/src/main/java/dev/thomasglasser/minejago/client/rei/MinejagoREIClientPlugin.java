package dev.thomasglasser.minejago.client.rei;

import dev.thomasglasser.minejago.client.rei.display.TeapotBrewingDisplay;
import dev.thomasglasser.minejago.client.rei.display.category.TeapotBrewingCategory;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;

@REIPluginClient
public class MinejagoREIClientPlugin implements REIClientPlugin
{
	@Override
	public void registerDisplays(DisplayRegistry registry)
	{
		registry.registerRecipeFiller(TeapotBrewingRecipe.class, MinejagoRecipeTypes.TEAPOT_BREWING.get(), TeapotBrewingDisplay::new);
	}

	@Override
	public void registerCategories(CategoryRegistry registry)
	{
		registry.add(
				new TeapotBrewingCategory()
		);

		MinejagoBlocks.TEAPOTS.values().forEach(block ->
				registry.addWorkstations(TeapotBrewingCategory.TEAPOT_BREWING, EntryStacks.of(block.get())));
	}

	@Override
	public void registerDisplaySerializer(DisplaySerializerRegistry registry)
	{
		registry.register(TeapotBrewingCategory.TEAPOT_BREWING, TeapotBrewingDisplay.serializer());
	}
}
