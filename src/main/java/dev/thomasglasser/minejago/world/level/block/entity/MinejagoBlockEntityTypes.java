package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MinejagoBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Minejago.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TeapotBlockEntity>> TEAPOT = register("teapot", () -> new BlockEntityType<>(TeapotBlockEntity::new, MinejagoBlocks.allPots().toArray(new Block[0])));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChiseledScrollShelfBlockEntity>> CHISELED_SCROLL_SHELF = register("chiseled_scroll_shelf", () -> new BlockEntityType<>(ChiseledScrollShelfBlockEntity::new, MinejagoBlocks.CHISELED_SCROLL_SHELF.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MinejagoBrushableBlockEntity>> BRUSHABLE = register("brushable", () -> new BlockEntityType<>(MinejagoBrushableBlockEntity::new, MinejagoBlocks.SUSPICIOUS_RED_SAND.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DragonHeadBlockEntity>> DRAGON_HEAD = register("dragon_head", () -> new BlockEntityType<>(DragonHeadBlockEntity::new, MinejagoBlocks.EARTH_DRAGON_HEAD.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DragonButtonBlockEntity>> DRAGON_BUTTON = register("dragon_button", () -> new BlockEntityType<>(DragonButtonBlockEntity::new, MinejagoBlocks.DRAGON_BUTTON.get()));

    private static <T extends BlockEntityType<?>> DeferredHolder<BlockEntityType<?>, T> register(String name, Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(name, type);
    }

    public static void init() {}
}
