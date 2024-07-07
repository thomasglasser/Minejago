package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoTreeFeatures;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.registration.DeferredBlock;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.level.block.BlockUtils;
import dev.thomasglasser.tommylib.api.world.level.block.LeavesSet;
import dev.thomasglasser.tommylib.api.world.level.block.WoodSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class MinejagoBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Minejago.MOD_ID);

    // Tea Trees
    public static final WoodSet ENCHANTED_WOOD_SET = registerWoodSet("enchanted", MapColor.COLOR_PURPLE, MapColor.COLOR_GRAY, () -> MinejagoBlockTags.ENCHANTED_LOGS, () -> MinejagoItemTags.ENCHANTED_LOGS);
    public static final LeavesSet FOCUS_LEAVES_SET = registerLeavesSet("focus", new TreeGrower("focus", 0.1F, Optional.empty(), Optional.empty(), Optional.of(MinejagoTreeFeatures.FOCUS), Optional.of(MinejagoTreeFeatures.FANCY_FOCUS), Optional.of(MinejagoTreeFeatures.FOCUS_BEES_005), Optional.of(MinejagoTreeFeatures.FANCY_FOCUS_BEES_005)));

    // POTS
    public static final DeferredBlock<TeapotBlock> TEAPOT = registerBlockAndItemAndWrap("teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instabreak().noOcclusion()), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS));
    public static final SortedMap<DyeColor, DeferredBlock<TeapotBlock>> TEAPOTS = teapots();
    public static final DeferredBlock<TeapotBlock> JASPOT = registerBlockAndItemAndWrap("jaspot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).instabreak().noOcclusion()), new Item.Properties().rarity(Rarity.EPIC), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS));
    public static final DeferredBlock<TeapotBlock> FLAME_TEAPOT = registerBlockAndItemAndWrap("flame_teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instabreak().noOcclusion()), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS));

    public static final DeferredBlock<DiscBlock> GOLD_DISC = registerBlockAndItemAndWrap("gold_disc", () -> new DiscBlock(BlockBehaviour.Properties.of().instabreak().pushReaction(PushReaction.DESTROY)), List.of(CreativeModeTabs.BUILDING_BLOCKS));
    public static final DeferredBlock<TopPostBlock> TOP_POST = registerBlockAndItemAndWrap("top_post", () -> new TopPostBlock(BlockBehaviour.Properties.of().instabreak().noCollission().pushReaction(PushReaction.DESTROY)), List.of(CreativeModeTabs.BUILDING_BLOCKS));
    public static final DeferredBlock<ChiseledScrollShelfBlock> CHISELED_SCROLL_SHELF = registerBlockAndItemAndWrap("chiseled_scroll_shelf", () -> new ChiseledScrollShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_BOOKSHELF)), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.REDSTONE_BLOCKS));
    public static final DeferredBlock<BrushableBlock> SUSPICIOUS_RED_SAND = registerBlockAndItemAndWrap("suspicious_red_sand", () -> new BrushableBlock(Blocks.RED_SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, BlockBehaviour.Properties.ofFullCopy(Blocks.SUSPICIOUS_SAND).mapColor(MapColor.COLOR_ORANGE)), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS));

    public static final DeferredBlock<DragonHeadBlock> EARTH_DRAGON_HEAD = register("earth_dragon_head", () -> new DragonHeadBlock(MinejagoEntityTypes.EARTH_DRAGON::get));

    private static <T extends Block> DeferredBlock<T> registerBlockAndItemAndWrap(
            String name,
            Supplier<T> blockFactory,
            List<ResourceKey<CreativeModeTab>> tabs) {
        return BlockUtils.registerBlockAndItemAndWrap(BLOCKS, name, blockFactory, MinejagoItems::register, tabs);
    }

    private static <T extends Block> DeferredBlock<T> registerBlockAndItemAndWrap(
            String name,
            Supplier<T> blockFactory,
            Item.Properties properties,
            List<ResourceKey<CreativeModeTab>> tabs) {
        return BlockUtils.registerBlockAndItemAndWrap(BLOCKS, name, blockFactory, MinejagoItems::register, properties, tabs);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> blockFactory) {
        return BlockUtils.register(BLOCKS, name, blockFactory);
    }

    private static WoodSet registerWoodSet(String name, MapColor logColor, MapColor strippedLogColor, Supplier<TagKey<Block>> logTag, Supplier<TagKey<Item>> logItemTag) {
        return BlockUtils.registerWoodSet(BLOCKS, name, logColor, strippedLogColor, MinejagoItems::register);
    }

    private static LeavesSet registerLeavesSet(String name, TreeGrower treeGrower) {
        return BlockUtils.registerLeavesSet(BLOCKS, name, treeGrower, MinejagoItems::register);
    }

    private static SortedMap<DyeColor, DeferredBlock<TeapotBlock>> teapots() {
        SortedMap<DyeColor, DeferredBlock<TeapotBlock>> map = new TreeMap<>();
        for (DyeColor color : DyeColor.values()) {
            map.put(color, registerBlockAndItemAndWrap(color.getName() + "_teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(color).instabreak().noOcclusion()), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS)));
        }
        return map;
    }

    public static List<Block> allPots() {
        List<Block> pots = new ArrayList<>();
        pots.add(TEAPOT.get());
        TEAPOTS.values().forEach(blockDeferredBlock -> pots.add(blockDeferredBlock.get()));
        pots.add(JASPOT.get());
        pots.add(FLAME_TEAPOT.get());
        return pots;
    }

    public static void init() {}
}
