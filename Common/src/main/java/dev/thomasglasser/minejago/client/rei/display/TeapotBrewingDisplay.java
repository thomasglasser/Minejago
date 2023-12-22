package dev.thomasglasser.minejago.client.rei.display;

import dev.thomasglasser.minejago.client.rei.display.category.TeapotBrewingCategory;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TeapotBrewingDisplay extends BasicDisplay
{
	private Potion base;
	private IntProvider cookingTime;
	private float experience;

	public TeapotBrewingDisplay(RecipeHolder<TeapotBrewingRecipe> recipe)
	{
		this(recipe.value().getBase(), EntryIngredients.ofIngredients(recipe.value().getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.value().getResultItem(BasicDisplay.registryAccess()))), Optional.of(recipe.id()), recipe.value().getCookingTime(), recipe.value().getExperience());
	}

	public TeapotBrewingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location, CompoundTag tag)
	{
		this(BuiltInRegistries.POTION.get(ResourceLocation.of(tag.getString("base"), ':')), inputs, outputs, location, IntProvider.CODEC.decode(NbtOps.INSTANCE, tag).get().orThrow().getFirst(), tag.getFloat("xp"));
	}

	public TeapotBrewingDisplay(Potion base, List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location, IntProvider cookTime, float xp)
	{
		super(inputs, outputs, location);
		this.cookingTime = cookTime;
		this.experience = xp;
		this.base = base;
	}

	public IntProvider getCookingTime()
	{
		return cookingTime;
	}

	public float getExperience()
	{
		return experience;
	}

	public Potion getBase()
	{
		return base;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier()
	{
		return TeapotBrewingCategory.TEAPOT_BREWING;
	}

	public static BasicDisplay.Serializer<TeapotBrewingDisplay> serializer()
	{
		return BasicDisplay.Serializer.of(TeapotBrewingDisplay::new, (display, tag) ->
		{
			tag.putString("base", BuiltInRegistries.POTION.getKey(display.getBase()).toString());
			IntProvider.CODEC.encodeStart(NbtOps.INSTANCE, display.cookingTime);
			tag.putFloat("xp", display.getExperience());
		});
	}
}