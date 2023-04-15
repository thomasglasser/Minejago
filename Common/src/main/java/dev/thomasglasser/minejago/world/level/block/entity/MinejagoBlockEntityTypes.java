package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.List;

public class MinejagoBlockEntityTypes
{
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Minejago.MOD_ID);

    public static final RegistryObject<BlockEntityType<TeapotBlockEntity>> TEAPOT = BLOCK_ENTITY_TYPES.register("teapot", () -> BlockEntityType.Builder.of(TeapotBlockEntity::new, allPots().toArray(new Block[0])).build(null));

    private static List<Block> allPots()
    {
        List<Block> pots = new ArrayList<>();
        pots.add(MinejagoBlocks.TEAPOT.get());
        pots.add(MinejagoBlocks.JASPOT.get());
        MinejagoBlocks.TEAPOTS.values().forEach(blockBlockRegistryObject -> pots.add(blockBlockRegistryObject.get()));
        return pots;
    }

    public static void init() {}
}
