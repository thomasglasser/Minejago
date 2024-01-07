package dev.thomasglasser.minejago.data.focus.modifier;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.focus.modifiers.FocusModifierProvider;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import dev.thomasglasser.minejago.world.focus.modifier.world.Weather;
import dev.thomasglasser.minejago.world.focus.modifier.world.WorldFocusModifier;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.level.block.Blocks.BLACK_CANDLE;
import static net.minecraft.world.level.block.Blocks.BLUE_CANDLE;
import static net.minecraft.world.level.block.Blocks.BROWN_CANDLE;
import static net.minecraft.world.level.block.Blocks.CANDLE;
import static net.minecraft.world.level.block.Blocks.CYAN_CANDLE;
import static net.minecraft.world.level.block.Blocks.GRAY_CANDLE;
import static net.minecraft.world.level.block.Blocks.GREEN_CANDLE;
import static net.minecraft.world.level.block.Blocks.LIGHT_BLUE_CANDLE;
import static net.minecraft.world.level.block.Blocks.LIGHT_GRAY_CANDLE;
import static net.minecraft.world.level.block.Blocks.LIME_CANDLE;
import static net.minecraft.world.level.block.Blocks.MAGENTA_CANDLE;
import static net.minecraft.world.level.block.Blocks.ORANGE_CANDLE;
import static net.minecraft.world.level.block.Blocks.PINK_CANDLE;
import static net.minecraft.world.level.block.Blocks.PURPLE_CANDLE;
import static net.minecraft.world.level.block.Blocks.RED_CANDLE;
import static net.minecraft.world.level.block.Blocks.WHITE_CANDLE;
import static net.minecraft.world.level.block.Blocks.YELLOW_CANDLE;

public class MinejagoFocusModifierProvider extends FocusModifierProvider
{
	public MinejagoFocusModifierProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture)
	{
		super(packOutput, completableFuture);
	}

	@Override
	protected void generate()
	{
		addBiomes();
		addBlockStates();
		addDimensions();
		addMobEffects();
		addEntities();
		addItemStacks();
		addStructures();
		addWorlds();
	}

	private void addBiomes()
	{
		addBiome(0.2, Operation.ADDITION, Biomes.MEADOW);
	}

	private void addBlockStates()
	{
		List<Block> candles = List.of(
				CANDLE,
				WHITE_CANDLE,
				ORANGE_CANDLE,
				MAGENTA_CANDLE,
				LIGHT_BLUE_CANDLE,
				YELLOW_CANDLE,
				LIME_CANDLE,
				PINK_CANDLE,
				GRAY_CANDLE,
				LIGHT_GRAY_CANDLE,
				CYAN_CANDLE,
				PURPLE_CANDLE,
				BLUE_CANDLE,
				BROWN_CANDLE,
				GREEN_CANDLE,
				RED_CANDLE,
				BLACK_CANDLE
		);
		for (int i = 1; i < 5; i++)
		{
			for (Block candle : candles)
			{
				add(new ResourceLocation(BuiltInRegistries.BLOCK.getKey(candle) + "_" + i), candle.defaultBlockState().setValue(CandleBlock.CANDLES, i).setValue(CandleBlock.LIT, true), 0.25 * i, Operation.ADDITION);
			}
		}
		add(0.05, Operation.ADDITION, Blocks.WATER);
		add(0.25, Operation.ADDITION, MinejagoBlocks.ENCHANTED_WOOD_SET.getAll().toArray(new Block[0]));
	}

	private void addDimensions()
	{
		addDimension(1, Operation.ADDITION, Level.END);
		addDimension(1, Operation.SUBTRACTION, Level.NETHER);
	}

	private void addMobEffects()
	{

	}

	private void addEntities()
	{

	}

	private void addItemStacks()
	{
		List<ItemStack> GOLDEN_WEAPONS = List.of(
				MinejagoItems.SCYTHE_OF_QUAKES.get().getDefaultInstance()
		);
		add(0.5, Operation.ADDITION, GOLDEN_WEAPONS.toArray(new ItemStack[] {}));
	}

	private void addStructures()
	{

	}

	private void addWorlds()
	{
		add(WorldFocusModifier.Builder.of(Minejago.modLoc("night"), 0.5, Operation.ADDITION)
				.dayTime(UniformInt.of(13000, 23000))
				.build());
		add(WorldFocusModifier.Builder.of(Minejago.modLoc("dusk"), 1, Operation.ADDITION)
				.dayTime(UniformInt.of(12000, 13000))
				.build());
		add(WorldFocusModifier.Builder.of(Minejago.modLoc("dawn"), 1, Operation.ADDITION)
				.dayTime(UniformInt.of(23000, 24000))
				.build());
		add(WorldFocusModifier.Builder.of(Minejago.modLoc("high"), 1, Operation.ADDITION)
				.y(UniformInt.of(100, Integer.MAX_VALUE))
				.build());
		add(WorldFocusModifier.Builder.of(Minejago.modLoc("thunder_rain"), 1.5, Operation.MULTIPLICATION)
				.weather(Weather.THUNDER_RAIN)
				.build());
		add(WorldFocusModifier.Builder.of(Minejago.modLoc("rain"), 1.25, Operation.MULTIPLICATION)
				.weather(Weather.RAIN)
				.build());
	}
}
