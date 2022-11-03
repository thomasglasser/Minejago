package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Minejago.MOD_ID);

    public static final RegistryObject<Block> TEAPOT = BLOCKS.register("teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of(Material.CLAY).instabreak().noOcclusion()));
}
