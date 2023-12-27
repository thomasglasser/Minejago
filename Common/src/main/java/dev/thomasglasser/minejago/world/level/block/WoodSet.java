package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public record WoodSet(ResourceLocation id,
                      RegistryObject<Block> planks,
                      RegistryObject<Block> log,
                      RegistryObject<Block> strippedLog,
                      RegistryObject<Block> wood,
                      RegistryObject<Block> strippedWood,
                      Supplier<TagKey<Block>> logsBlockTag,
                      Supplier<TagKey<Item>> logsItemTag)
{
	public List<Block> getAll()
	{
		return List.of(planks.get(), log.get(), strippedLog.get(), wood.get(), strippedWood.get());
	}
}
