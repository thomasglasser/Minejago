package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record LeavesSet(ResourceLocation id,
                        RegistryObject<Block> leaves,
                        RegistryObject<Block> sapling,
                        RegistryObject<Block> pottedSapling)
{}
