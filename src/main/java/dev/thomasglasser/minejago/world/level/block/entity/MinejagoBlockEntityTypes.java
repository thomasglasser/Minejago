package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoBlockEntityTypes
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Minejago.MOD_ID);

    public static final RegistryObject<BlockEntityType<TeapotBlockEntity>> TEAPOT = BLOCK_ENTITY_TYPES.register("teapot", () -> BlockEntityType.Builder.of(TeapotBlockEntity::new, MinejagoBlocks.TEAPOT.get()).build(null));
}
