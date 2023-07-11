package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.BlockRegistryObject;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MinejagoBlocks
{
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Minejago.MOD_ID);

    public static final Function<Supplier<Block>, BlockItem> BLOCK_ITEM_FUNCTION = ((block) -> new BlockItem(block.get(), new Item.Properties()));
    public static final BiFunction<Supplier<Block>, Item.Properties, BlockItem> BLOCK_ITEM_WITH_PROPERTIES_FUNCTION = ((block, properties) -> new BlockItem(block.get(), properties));

    // POTS
    public static final BlockRegistryObject<Block> TEAPOT = registerBlockAndItemAndWrap("teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instabreak().noOcclusion()), CreativeModeTabs.FUNCTIONAL_BLOCKS);
    public static final BlockRegistryObject<Block> JASPOT = registerBlockAndItemAndWrap("jaspot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).instabreak().noOcclusion()), CreativeModeTabs.FUNCTIONAL_BLOCKS);
    public static final Map<DyeColor, BlockRegistryObject<Block>> TEAPOTS = teapots();

    public static final BlockRegistryObject<Block> GOLD_DISC = registerBlockAndItemAndWrap("gold_disc", () -> new DiscBlock(BlockBehaviour.Properties.of().instabreak().pushReaction(PushReaction.DESTROY)), CreativeModeTabs.BUILDING_BLOCKS);
    public static final BlockRegistryObject<Block> TOP_POST = registerBlockAndItemAndWrap("top_post", () -> new TopPostBlock(BlockBehaviour.Properties.of().instabreak().noCollission().pushReaction(PushReaction.DESTROY)), CreativeModeTabs.BUILDING_BLOCKS);
    public static final BlockRegistryObject<Block> CHISELED_SCROLL_SHELF = registerBlockAndItemAndWrap("chiseled_scroll_shelf", () -> new ChiseledScrollShelfBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_BOOKSHELF)), CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.REDSTONE_BLOCKS);
    public static final BlockRegistryObject<Block> SUSPICIOUS_RED_SAND = registerBlockAndItemAndWrap("suspicious_red_sand", () -> new MinejagoBrushableBlock(Blocks.RED_SAND, BlockBehaviour.Properties.copy(Blocks.SUSPICIOUS_SAND).mapColor(MapColor.COLOR_ORANGE), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED), CreativeModeTabs.FUNCTIONAL_BLOCKS);

    public static final BlockRegistryObject<Block> EARTH_DRAGON_HEAD = registerBlockAndWrap("earth_dragon_head", () -> new DragonHeadBlock(MinejagoEntityTypes.EARTH_DRAGON::get));

    @SafeVarargs
    private static BlockRegistryObject<Block> registerBlockAndItemAndWrap(
            String name,
            Supplier<Block> blockFactory,
            ResourceKey<CreativeModeTab>... tabs)
    {
        RegistryObject<Block> block = BLOCKS.register(name, blockFactory);
        MinejagoItems.register(name, () -> BLOCK_ITEM_FUNCTION.apply(block), tabs);
        return BlockRegistryObject.wrap(block);
    }

    @SafeVarargs
    private static BlockRegistryObject<Block> registerBlockAndItemAndWrap(
            String name,
            Supplier<Block> blockFactory,
            Item.Properties properties,
            ResourceKey<CreativeModeTab>... tabs)
    {
        RegistryObject<Block> block = BLOCKS.register(name, blockFactory);
        MinejagoItems.register(name, () -> BLOCK_ITEM_WITH_PROPERTIES_FUNCTION.apply(block, properties), tabs);
        return BlockRegistryObject.wrap(block);
    }

    private static BlockRegistryObject<Block> registerBlockAndWrap(String name, Supplier<Block> block)
    {
        return BlockRegistryObject.wrap(BLOCKS.register(name, block));
    }

    private static Map<DyeColor, BlockRegistryObject<Block>> teapots()
    {
        Map<DyeColor, BlockRegistryObject<Block>> map = new HashMap<>();
        for (DyeColor color : DyeColor.values())
        {
            map.put(color, registerBlockAndItemAndWrap(color.getName() + "_teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(color).instabreak().noOcclusion()), CreativeModeTabs.FUNCTIONAL_BLOCKS));
        }
        return map;
    }

    public static List<Block> allPots()
    {
        List<Block> pots = new ArrayList<>();
        pots.add(MinejagoBlocks.TEAPOT.get());
        pots.add(MinejagoBlocks.JASPOT.get());
        MinejagoBlocks.TEAPOTS.values().forEach(blockBlockRegistryObject -> pots.add(blockBlockRegistryObject.get()));
        return pots;
    }

    public static void init() {}
}
