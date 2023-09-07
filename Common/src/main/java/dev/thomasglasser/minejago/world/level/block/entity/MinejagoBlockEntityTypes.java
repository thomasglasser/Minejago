package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MinejagoBlockEntityTypes
{
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Minejago.MOD_ID);

    public static final RegistryObject<BlockEntityType<TeapotBlockEntity>> TEAPOT = BLOCK_ENTITY_TYPES.register("teapot", () -> BlockEntityType.Builder.of(TeapotBlockEntity::new, MinejagoBlocks.allPots().toArray(new Block[0])).build(null));
    public static final RegistryObject<BlockEntityType<ChiseledScrollShelfBlockEntity>> CHISELED_SCROLL_SHELF = BLOCK_ENTITY_TYPES.register("chiseled_scroll_shelf", () -> BlockEntityType.Builder.of(ChiseledScrollShelfBlockEntity::new, MinejagoBlocks.CHISELED_SCROLL_SHELF.get()).build(null));
    public static final RegistryObject<BlockEntityType<MinejagoBrushableBlockEntity>> BRUSHABLE = BLOCK_ENTITY_TYPES.register("brushable", () -> BlockEntityType.Builder.of(MinejagoBrushableBlockEntity::new, MinejagoBlocks.SUSPICIOUS_RED_SAND.get()).build(null));
    public static final RegistryObject<BlockEntityType<DragonHeadBlockEntity>> DRAGON_HEAD = BLOCK_ENTITY_TYPES.register("dragon_head", () -> BlockEntityType.Builder.of(DragonHeadBlockEntity::new, MinejagoBlocks.EARTH_DRAGON_HEAD.get()).build(null));

    public static void init() {}
}
