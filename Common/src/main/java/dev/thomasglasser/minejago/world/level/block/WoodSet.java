package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.registration.BlockRegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public record WoodSet(ResourceLocation id,
                      BlockRegistryObject<Block> planks,
                      BlockRegistryObject<Block> log,
                      BlockRegistryObject<Block> strippedLog,
                      BlockRegistryObject<Block> wood,
                      BlockRegistryObject<Block> strippedWood,
                      Supplier<TagKey<Block>> logsBlockTag,
                      Supplier<TagKey<Item>> logsItemTag)
{
	public List<Block> getAll()
	{
		return List.of(planks.get(), log.get(), strippedLog.get(), wood.get(), strippedWood.get());
	}
}
