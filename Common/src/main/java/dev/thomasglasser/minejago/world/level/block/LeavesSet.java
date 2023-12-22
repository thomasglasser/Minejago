package dev.thomasglasser.minejago.world.level.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public record LeavesSet(ResourceLocation id,
                        Supplier<Block> leaves,
                        Supplier<Block> sapling,
                        Supplier<Block> pottedSapling)
{}
