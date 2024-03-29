package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class MinejagoBlockEntityTypes
{
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(BuiltInRegistries.BLOCK_ENTITY_TYPE, Minejago.MOD_ID);
    
    public static final RegistryObject<BlockEntityType<TeapotBlockEntity>> TEAPOT = register("teapot", () -> BlockEntityType.Builder.of(TeapotBlockEntity::new, MinejagoBlocks.allPots().toArray(new Block[0])).build(null));
    public static final RegistryObject<BlockEntityType<ChiseledScrollShelfBlockEntity>> CHISELED_SCROLL_SHELF = register("chiseled_scroll_shelf", () -> BlockEntityType.Builder.of(ChiseledScrollShelfBlockEntity::new, MinejagoBlocks.CHISELED_SCROLL_SHELF.get()).build(null));
    public static final RegistryObject<BlockEntityType<MinejagoBrushableBlockEntity>> BRUSHABLE = register("brushable", () -> BlockEntityType.Builder.of(MinejagoBrushableBlockEntity::new, MinejagoBlocks.SUSPICIOUS_RED_SAND.get()).build(null));
    public static final RegistryObject<BlockEntityType<DragonHeadBlockEntity>> DRAGON_HEAD = register("dragon_head", () -> BlockEntityType.Builder.of(DragonHeadBlockEntity::new, MinejagoBlocks.EARTH_DRAGON_HEAD.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistryObject<T> register(String name, Supplier<T> type)
    {
        return BLOCK_ENTITY_TYPES.register(name, type);
    }

    public static void init() {}
}
