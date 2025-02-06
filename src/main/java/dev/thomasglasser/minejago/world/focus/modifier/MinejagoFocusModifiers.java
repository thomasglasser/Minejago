package dev.thomasglasser.minejago.world.focus.modifier;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.vehicle.MinejagoBoatTypes;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import java.util.Optional;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;

public class MinejagoFocusModifiers {
    public static void bootstrap(BootstrapContext<FocusModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        // Biomes
        context.register(biome("mountains"), new LocationFocusModifier(LocationPredicate.Builder.location().setBiomes(biomes.getOrThrow(BiomeTags.IS_MOUNTAIN)).build(), Operation.ADDITION, 0.2));

        // Blocks
        for (int i = 1; i < 5; i++) {
            context.register(block(i + "_lit_candle" + (i > 1 ? "s" : "")), new BlockFocusModifier(BlockPredicate.Builder.block().of(BlockTags.CANDLES).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CandleBlock.LIT, true).hasProperty(CandleBlock.CANDLES, i)).build(), Operation.ADDITION, 0.25 * i));
        }
        context.register(block("water"), new BlockFocusModifier(BlockPredicate.Builder.block().of(Blocks.WATER).build(), Operation.ADDITION, 0.05));
        context.register(block("enchanted_wood"), new BlockFocusModifier(BlockPredicate.Builder.block().of(MinejagoBlocks.ENCHANTED_WOOD_SET.getAllBlocks()).build(), Operation.ADDITION, 0.25));
        context.register(block("scroll_shelf"), new BlockFocusModifier(BlockPredicate.Builder.block().of(MinejagoBlocks.SCROLL_SHELF.get()).build(), Operation.ADDITION, 0.2));

        // Dimensions
        context.register(dimension("the_end"), new LocationFocusModifier(LocationPredicate.Builder.inDimension(Level.END).build(), Operation.ADDITION, 1));
        context.register(dimension("the_nether"), new LocationFocusModifier(LocationPredicate.Builder.inDimension(Level.NETHER).build(), Operation.SUBTRACTION, 1));

        // Entities
        CompoundTag enchanted = new CompoundTag();
        enchanted.putString("Type", MinejagoBoatTypes.ENCHANTED.getValue().getSerializedName());
        context.register(entity("enchanted_boats"), new EntityFocusModifier(EntityPredicate.Builder.entity().entityType(new EntityTypePredicate(HolderSet.direct(EntityType.BOAT.builtInRegistryHolder(), EntityType.CHEST_BOAT.builtInRegistryHolder()))).nbt(new NbtPredicate(enchanted)).build(), Optional.of(false), Operation.ADDITION, 0.25));

        // Items
        context.register(item("golden_weapons"), new ItemFocusModifier(ItemPredicate.Builder.item().of(MinejagoItemTags.GOLDEN_WEAPONS).build(), Operation.ADDITION, 0.5));

        // World
        context.register(world("night"), WorldFocusModifier.Builder.of(0.5, Operation.ADDITION)
                .dayTime(MinMaxBounds.Ints.between(13000, 23000))
                .build());
        context.register(world("dusk"), WorldFocusModifier.Builder.of(1, Operation.ADDITION)
                .dayTime(MinMaxBounds.Ints.between(12000, 13000))
                .build());
        context.register(world("dawn"), WorldFocusModifier.Builder.of(1, Operation.ADDITION)
                .dayTime(MinMaxBounds.Ints.between(23000, 24000))
                .build());
        context.register(world("high"), WorldFocusModifier.Builder.of(1, Operation.ADDITION)
                .y(MinMaxBounds.Ints.atLeast(100))
                .build());
        context.register(world("thunder_rain"), WorldFocusModifier.Builder.of(1.5, Operation.MULTIPLICATION)
                .weather(Weather.THUNDER_RAIN)
                .build());
        context.register(world("rain"), WorldFocusModifier.Builder.of(1.25, Operation.MULTIPLICATION)
                .weather(Weather.RAIN)
                .build());
    }

    private static ResourceKey<FocusModifier> create(String name) {
        return ResourceKey.create(MinejagoRegistries.FOCUS_MODIFIER, Minejago.modLoc(name));
    }

    private static ResourceKey<FocusModifier> biome(String name) {
        return create("biome/" + name);
    }

    private static ResourceKey<FocusModifier> block(String name) {
        return create("block/" + name);
    }

    private static ResourceKey<FocusModifier> dimension(String name) {
        return create("dimension/" + name);
    }

    private static ResourceKey<FocusModifier> entity(String name) {
        return create("entity/" + name);
    }

    private static ResourceKey<FocusModifier> item(String name) {
        return create("item/" + name);
    }

    private static ResourceKey<FocusModifier> world(String name) {
        return create("world/" + name);
    }
}
