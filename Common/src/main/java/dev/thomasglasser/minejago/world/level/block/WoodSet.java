package dev.thomasglasser.minejago.world.level.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public record WoodSet(ResourceLocation id,
                      Supplier<Block> planks,
                      Supplier<Block> log,
                      Supplier<Block> strippedLog,
                      Supplier<Block> wood,
                      Supplier<Block> strippedWood,
                      Supplier<TagKey<Block>> logsBlockTag,
                      Supplier<TagKey<Item>> logsItemTag)
{}
