package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoTreeFeatures;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MinejagoBlocks
{
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(BuiltInRegistries.BLOCK, Minejago.MOD_ID);
    public static final Function<RegistryObject<? extends Block>, BlockItem> BLOCK_ITEM_FUNCTION = ((block) -> new BlockItem(block.get(), new Item.Properties()));
    public static final BiFunction<RegistryObject<Block>, Item.Properties, BlockItem> BLOCK_ITEM_WITH_PROPERTIES_FUNCTION = ((block, properties) -> new BlockItem(block.get(), properties));

    // POTS
    public static final RegistryObject<TeapotBlock> TEAPOT = registerBlockAndItemAndWrap("teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instabreak().noOcclusion()), CreativeModeTabs.FUNCTIONAL_BLOCKS);
    public static final SortedMap<DyeColor, RegistryObject<TeapotBlock>> TEAPOTS = teapots();
    public static final RegistryObject<TeapotBlock> JASPOT = registerBlockAndItemAndWrap("jaspot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).instabreak().noOcclusion()), CreativeModeTabs.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<TeapotBlock> FLAME_TEAPOT = registerBlockAndItemAndWrap("flame_teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instabreak().noOcclusion()), CreativeModeTabs.FUNCTIONAL_BLOCKS);

    public static final RegistryObject<DiscBlock> GOLD_DISC = registerBlockAndItemAndWrap("gold_disc", () -> new DiscBlock(BlockBehaviour.Properties.of().instabreak().pushReaction(PushReaction.DESTROY)), CreativeModeTabs.BUILDING_BLOCKS);
    public static final RegistryObject<TopPostBlock> TOP_POST = registerBlockAndItemAndWrap("top_post", () -> new TopPostBlock(BlockBehaviour.Properties.of().instabreak().noCollission().pushReaction(PushReaction.DESTROY)), CreativeModeTabs.BUILDING_BLOCKS);
    public static final RegistryObject<ChiseledScrollShelfBlock> CHISELED_SCROLL_SHELF = registerBlockAndItemAndWrap("chiseled_scroll_shelf", () -> new ChiseledScrollShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_BOOKSHELF)), CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.REDSTONE_BLOCKS);
    public static final RegistryObject<MinejagoBrushableBlock> SUSPICIOUS_RED_SAND = registerBlockAndItemAndWrap("suspicious_red_sand", () -> new MinejagoBrushableBlock(Blocks.RED_SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, BlockBehaviour.Properties.ofFullCopy(Blocks.SUSPICIOUS_SAND).mapColor(MapColor.COLOR_ORANGE)), CreativeModeTabs.FUNCTIONAL_BLOCKS);

    public static final RegistryObject<DragonHeadBlock> EARTH_DRAGON_HEAD = register("earth_dragon_head", () -> new DragonHeadBlock(MinejagoEntityTypes.EARTH_DRAGON::get));

    // Tea Trees
    public static final WoodSet ENCHANTED_WOOD_SET = registerWoodSet("enchanted", MapColor.COLOR_PURPLE, MapColor.COLOR_GRAY, () -> MinejagoBlockTags.ENCHANTED_LOGS, () -> MinejagoItemTags.ENCHANTED_LOGS);
    public static final LeavesSet FOCUS_LEAVES_SET = registerLeavesSet("focus", new TreeGrower("focus", 0.1F, Optional.empty(), Optional.empty(), Optional.of(MinejagoTreeFeatures.FOCUS), Optional.of(MinejagoTreeFeatures.FANCY_FOCUS), Optional.of(MinejagoTreeFeatures.FOCUS_BEES_005), Optional.of(MinejagoTreeFeatures.FANCY_FOCUS_BEES_005)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block)
    {
        return BLOCKS.register(name, block);
    }

    @SafeVarargs
    private static <T extends Block> RegistryObject<T> registerBlockAndItemAndWrap(
            String name,
            Supplier<T> blockFactory,
            ResourceKey<CreativeModeTab>... tabs)
    {
        RegistryObject<T> block = register(name, blockFactory);
        MinejagoItems.register(name, () -> BLOCK_ITEM_FUNCTION.apply(block), tabs);
        return block;
    }

    @SafeVarargs
    private static RegistryObject<Block> registerBlockAndItemAndWrap(
            String name,
            Supplier<Block> blockFactory,
            Item.Properties properties,
            ResourceKey<CreativeModeTab>... tabs)
    {
        RegistryObject<Block> block = register(name, blockFactory);
        MinejagoItems.register(name, () -> BLOCK_ITEM_WITH_PROPERTIES_FUNCTION.apply(block, properties), tabs);
        return block;
    }

    private static SortedMap<DyeColor, RegistryObject<TeapotBlock>> teapots()
    {
        SortedMap<DyeColor, RegistryObject<TeapotBlock>> map = new TreeMap<>();
        for (DyeColor color : DyeColor.values())
        {
            map.put(color, registerBlockAndItemAndWrap(color.getName() + "_teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(color).instabreak().noOcclusion()), CreativeModeTabs.FUNCTIONAL_BLOCKS));
        }
        return map;
    }

    private static WoodSet registerWoodSet(String id, MapColor mapColor, MapColor logMapColor, Supplier<TagKey<Block>> logsBlockTag, Supplier<TagKey<Item>> logsItemTag)
    {
        return new WoodSet(Minejago.modLoc(id),
                registerBlockAndItemAndWrap(id + "_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()), CreativeModeTabs.BUILDING_BLOCKS),
                registerBlockAndItemAndWrap(id + "_log", () -> Blocks.log(mapColor, logMapColor), CreativeModeTabs.BUILDING_BLOCKS, CreativeModeTabs.NATURAL_BLOCKS),
                registerBlockAndItemAndWrap("stripped_" + id + "_log", () -> Blocks.log(mapColor, mapColor), CreativeModeTabs.BUILDING_BLOCKS),
                registerBlockAndItemAndWrap(id + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()), CreativeModeTabs.BUILDING_BLOCKS),
                registerBlockAndItemAndWrap("stripped_" + id + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()), CreativeModeTabs.BUILDING_BLOCKS),
                logsBlockTag,
                logsItemTag);
    }

    private static LeavesSet registerLeavesSet(String id, TreeGrower treeGrower)
    {
        RegistryObject<Block> sapling = registerBlockAndItemAndWrap(id + "_sapling", () -> new SaplingBlock(treeGrower, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), CreativeModeTabs.NATURAL_BLOCKS);
        return new LeavesSet(Minejago.modLoc(id),
                registerBlockAndItemAndWrap(id + "_leaves", () -> Blocks.leaves(SoundType.GRASS), CreativeModeTabs.NATURAL_BLOCKS),
                sapling,
                register("potted_" + id + "_sapling", () -> Blocks.flowerPot(sapling.get())));
    }

    public static List<Block> allPots()
    {
        List<Block> pots = new ArrayList<>();
        pots.add(TEAPOT.get());
        TEAPOTS.values().forEach(blockRegistryObject -> pots.add(blockRegistryObject.get()));
        pots.add(JASPOT.get());
        pots.add(FLAME_TEAPOT.get());
        return pots;
    }

    public static void init() {}
}
