package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoTreeFeatures;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import dev.thomasglasser.tommylib.api.world.level.block.BlockUtils;
import dev.thomasglasser.tommylib.api.world.level.block.LeavesSet;
import dev.thomasglasser.tommylib.api.world.level.block.WoodSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;

public class MinejagoBlocks
{
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(BuiltInRegistries.BLOCK, Minejago.MOD_ID);

    // POTS
    public static final RegistryObject<TeapotBlock> TEAPOT = registerBlockAndItemAndWrap("teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instabreak().noOcclusion()), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS));
    public static final SortedMap<DyeColor, RegistryObject<TeapotBlock>> TEAPOTS = teapots();
    public static final RegistryObject<TeapotBlock> JASPOT = registerBlockAndItemAndWrap("jaspot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).instabreak().noOcclusion()), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS));
    public static final RegistryObject<TeapotBlock> FLAME_TEAPOT = registerBlockAndItemAndWrap("flame_teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instabreak().noOcclusion()), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS));

    public static final RegistryObject<DiscBlock> GOLD_DISC = registerBlockAndItemAndWrap("gold_disc", () -> new DiscBlock(BlockBehaviour.Properties.of().instabreak().pushReaction(PushReaction.DESTROY)), List.of(CreativeModeTabs.BUILDING_BLOCKS));
    public static final RegistryObject<TopPostBlock> TOP_POST = registerBlockAndItemAndWrap("top_post", () -> new TopPostBlock(BlockBehaviour.Properties.of().instabreak().noCollission().pushReaction(PushReaction.DESTROY)), List.of(CreativeModeTabs.BUILDING_BLOCKS));
    public static final RegistryObject<ChiseledScrollShelfBlock> CHISELED_SCROLL_SHELF = registerBlockAndItemAndWrap("chiseled_scroll_shelf", () -> new ChiseledScrollShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_BOOKSHELF)), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS, CreativeModeTabs.REDSTONE_BLOCKS));
    public static final RegistryObject<MinejagoBrushableBlock> SUSPICIOUS_RED_SAND = registerBlockAndItemAndWrap("suspicious_red_sand", () -> new MinejagoBrushableBlock(Blocks.RED_SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, BlockBehaviour.Properties.ofFullCopy(Blocks.SUSPICIOUS_SAND).mapColor(MapColor.COLOR_ORANGE)), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS));

    public static final RegistryObject<DragonHeadBlock> EARTH_DRAGON_HEAD = register("earth_dragon_head", () -> new DragonHeadBlock(MinejagoEntityTypes.EARTH_DRAGON::get));

    // Tea Trees
    public static final WoodSet ENCHANTED_WOOD_SET = registerWoodSet("enchanted", MapColor.COLOR_PURPLE, MapColor.COLOR_GRAY, () -> MinejagoBlockTags.ENCHANTED_LOGS, () -> MinejagoItemTags.ENCHANTED_LOGS);
    public static final LeavesSet FOCUS_LEAVES_SET = registerLeavesSet("focus", new TreeGrower("focus", 0.1F, Optional.empty(), Optional.empty(), Optional.of(MinejagoTreeFeatures.FOCUS), Optional.of(MinejagoTreeFeatures.FANCY_FOCUS), Optional.of(MinejagoTreeFeatures.FOCUS_BEES_005), Optional.of(MinejagoTreeFeatures.FANCY_FOCUS_BEES_005)));

    private static <T extends Block> RegistryObject<T> registerBlockAndItemAndWrap(
            String name,
            Supplier<T> blockFactory,
            List<ResourceKey<CreativeModeTab>> tabs)
    {
        return BlockUtils.registerBlockAndItemAndWrap(BLOCKS, name, blockFactory, MinejagoItems::register, tabs);
    }

    private static RegistryObject<Block> registerBlockAndItemAndWrap(
            String name,
            Supplier<Block> blockFactory,
            Item.Properties properties,
            List<ResourceKey<CreativeModeTab>> tabs)
    {
        return BlockUtils.registerBlockAndItemAndWrap(BLOCKS, name, blockFactory, MinejagoItems::register, properties, tabs);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockFactory)
    {
        return BlockUtils.register(BLOCKS, name, blockFactory);
    }

    private static WoodSet registerWoodSet(String name, MapColor logColor, MapColor strippedLogColor, Supplier<TagKey<Block>> logTag, Supplier<TagKey<Item>> logItemTag)
    {
        return BlockUtils.registerWoodSet(BLOCKS, Minejago.modLoc(name), logColor, strippedLogColor, logTag, logItemTag, MinejagoItems::register);
    }

    private static LeavesSet registerLeavesSet(String name, TreeGrower treeGrower)
    {
        return BlockUtils.registerLeavesSet(BLOCKS, Minejago.modLoc(name), treeGrower, MinejagoItems::register);
    }

    private static SortedMap<DyeColor, RegistryObject<TeapotBlock>> teapots()
    {
        SortedMap<DyeColor, RegistryObject<TeapotBlock>> map = new TreeMap<>();
        for (DyeColor color : DyeColor.values())
        {
            map.put(color, registerBlockAndItemAndWrap(color.getName() + "_teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(color).instabreak().noOcclusion()), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS)));
        }
        return map;
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
