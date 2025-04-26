package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoTreeFeatures;
import dev.thomasglasser.minejago.world.entity.vehicle.MinejagoBoatTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.state.properties.MinejagoWoodTypes;
import dev.thomasglasser.tommylib.api.registration.DeferredBlock;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.level.block.BlockUtils;
import dev.thomasglasser.tommylib.api.world.level.block.LeavesSet;
import dev.thomasglasser.tommylib.api.world.level.block.WoodSet;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import java.util.Optional;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class MinejagoBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Minejago.MOD_ID);

    public static final DeferredBlock<DiscBlock> GOLD_DISC = registerBlockAndItemAndWrap("gold_disc", () -> new DiscBlock(BlockBehaviour.Properties.of().instabreak().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<TopPostBlock> TOP_POST = registerBlockAndItemAndWrap("top_post", () -> new TopPostBlock(BlockBehaviour.Properties.of().instabreak().noCollission().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SCROLL_SHELF = registerBlockAndItemAndWrap("scroll_shelf", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BOOKSHELF)));
    public static final DeferredBlock<ChiseledScrollShelfBlock> CHISELED_SCROLL_SHELF = registerBlockAndItemAndWrap("chiseled_scroll_shelf", () -> new ChiseledScrollShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_BOOKSHELF)));
    public static final DeferredBlock<BrushableBlock> SUSPICIOUS_RED_SAND = registerBlockAndItemAndWrap("suspicious_red_sand", () -> new BrushableBlock(Blocks.RED_SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, BlockBehaviour.Properties.ofFullCopy(Blocks.SUSPICIOUS_SAND).mapColor(MapColor.COLOR_ORANGE)));
    public static final DeferredBlock<?> DRAGON_BUTTON = registerBlockAndItemAndWrap("dragon_button", () -> new DragonButtonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<FreezingIceBlock> FREEZING_ICE = registerBlockAndItemAndWrap("freezing_ice", () -> new FreezingIceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.ICE).strength(2.8F).friction(0.989F).sound(SoundType.GLASS)));

    // Tea Trees
    public static final WoodSet ENCHANTED_WOOD_SET = registerWoodSet("enchanted", MapColor.COLOR_PURPLE, MapColor.COLOR_GRAY, MinejagoWoodTypes::getEnchanted, MinejagoBoatTypes.ENCHANTED.getValue());
    public static final LeavesSet FOCUS_LEAVES_SET = registerLeavesSet("focus", new TreeGrower("focus", 0.1F, Optional.empty(), Optional.empty(), Optional.of(MinejagoTreeFeatures.FOCUS), Optional.of(MinejagoTreeFeatures.FANCY_FOCUS), Optional.of(MinejagoTreeFeatures.FOCUS_BEES_005), Optional.of(MinejagoTreeFeatures.FANCY_FOCUS_BEES_005)));

    // Teapots
    private static final BiFunction<MapColor, Holder<Item>, TeapotBlock> TEAPOT_FUNCTION = (color, item) -> new TeapotBlock(BlockBehaviour.Properties.of().mapColor(color).instabreak().noOcclusion(), item);
    public static final DeferredBlock<TeapotBlock> TEAPOT = registerBlockAndItemAndWrap("teapot", () -> TEAPOT_FUNCTION.apply(MapColor.COLOR_ORANGE, MinejagoItems.FILLED_TEACUP));
    public static final SortedMap<DyeColor, DeferredBlock<TeapotBlock>> TEAPOTS = teapots();
    public static final DeferredBlock<TeapotBlock> JASPOT = registerBlockAndItemAndWrap("jaspot", () -> TEAPOT_FUNCTION.apply(MapColor.COLOR_LIGHT_BLUE, MinejagoItems.MINICUP), new Item.Properties().rarity(Rarity.UNCOMMON));

    private static <T extends Block> DeferredBlock<T> registerBlockAndItemAndWrap(
            String name,
            Supplier<T> blockFactory) {
        return BlockUtils.registerBlockAndItemAndWrap(BLOCKS, name, blockFactory, MinejagoItems::register);
    }

    private static <T extends Block> DeferredBlock<T> registerBlockAndItemAndWrap(
            String name,
            Supplier<T> blockFactory,
            Item.Properties itemProperties) {
        return BlockUtils.registerBlockAndItemAndWrap(BLOCKS, name, blockFactory, MinejagoItems::register, block -> new BlockItem(block, itemProperties));
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> blockFactory) {
        return BlockUtils.register(BLOCKS, name, blockFactory);
    }

    private static WoodSet registerWoodSet(String name, MapColor logColor, MapColor strippedLogColor, Supplier<WoodType> woodType, Boat.Type boatType) {
        return BlockUtils.registerWoodSet(BLOCKS, name, logColor, strippedLogColor, woodType, boatType, MinejagoItems::register);
    }

    private static LeavesSet registerLeavesSet(String name, TreeGrower treeGrower) {
        return BlockUtils.registerLeavesSet(BLOCKS, name, treeGrower, MinejagoItems::register);
    }

    private static SortedMap<DyeColor, DeferredBlock<TeapotBlock>> teapots() {
        SortedMap<DyeColor, DeferredBlock<TeapotBlock>> map = new TreeMap<>();
        for (DyeColor color : DyeColor.values()) {
            map.put(color, registerBlockAndItemAndWrap(color.getName() + "_teapot", () -> TEAPOT_FUNCTION.apply(color.getMapColor(), MinejagoItems.FILLED_TEACUPS.get(color))));
        }
        return map;
    }

    public static SortedSet<Block> allPots() {
        SortedSet<Block> pots = new ReferenceLinkedOpenHashSet<>();
        pots.add(TEAPOT.get());
        TEAPOTS.values().forEach(blockDeferredBlock -> pots.add(blockDeferredBlock.get()));
        pots.add(JASPOT.get());
        return pots;
    }

    public static void init() {}
}
