package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.registration.BlockRegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record LeavesSet(ResourceLocation id,
                        BlockRegistryObject<Block> leaves,
                        BlockRegistryObject<Block> sapling,
                        BlockRegistryObject<Block> pottedSapling)
{}
