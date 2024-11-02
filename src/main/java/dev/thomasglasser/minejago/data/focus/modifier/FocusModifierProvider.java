package dev.thomasglasser.minejago.data.focus.modifier;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Operation;
import dev.thomasglasser.minejago.world.focus.modifier.blockstate.BlockStateFocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.entity.EntityFocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.itemstack.ItemStackFocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.resourcekey.ResourceKeyFocusModifier;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;

public abstract class FocusModifierProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;
    private final List<FocusModifier> focusModifiers = new ArrayList<>();
    private final CompletableFuture<HolderLookup.Provider> registries;

    public FocusModifierProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, Minejago.MOD_ID + "/focus_modifiers");
        this.registries = completableFuture;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return this.registries.thenCompose(provider -> {
            Set<ResourceLocation> set = new HashSet<>();
            List<CompletableFuture<?>> list = new ArrayList<>();
            Consumer<FocusModifier> consumer = modifier -> {
                if (!set.add(ResourceLocation.fromNamespaceAndPath(modifier.getId().getNamespace(), modifier.getType() + "/" + modifier.getId().getPath()))) {
                    throw new IllegalStateException("Duplicate focus modifier " + modifier.getId());
                } else {
                    Path path = this.pathProvider.json(ResourceLocation.fromNamespaceAndPath(modifier.getId().getNamespace(), modifier.getType() + "/" + modifier.getId().getPath()));
                    list.add(DataProvider.saveStable(output, modifier.toJson(), path));
                }
            };

            generate();

            focusModifiers.forEach(consumer);

            return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
        });
    }

    protected abstract void generate();

    protected void add(FocusModifier... modifiers) {
        focusModifiers.addAll(Arrays.asList(modifiers));
    }

    @SafeVarargs
    protected final void addBiome(double modifier, Operation operation, ResourceKey<Biome>... biomes) {
        for (ResourceKey<Biome> biome : biomes) {
            addBiome(biome.location(), biome, modifier, operation);
        }
    }

    protected void addBiome(ResourceLocation location, ResourceKey<Biome> biome, double modifier, Operation operation) {
        add(new ResourceKeyFocusModifier<>(location, biome, modifier, operation));
    }

    protected void add(double modifier, Operation operation, BlockState... states) {
        for (BlockState state : states) {
            add(BuiltInRegistries.BLOCK.getKey(state.getBlock()), state, modifier, operation);
        }
    }

    protected void add(double modifier, Operation operation, Block... blocks) {
        for (Block block : blocks) {
            add(BuiltInRegistries.BLOCK.getKey(block), block, modifier, operation);
        }
    }

    protected void add(ResourceLocation location, BlockState state, double modifier, Operation operation) {
        add(new BlockStateFocusModifier(location, state, null, modifier, operation));
    }

    protected void add(ResourceLocation location, Block block, double modifier, Operation operation) {
        add(new BlockStateFocusModifier(location, null, block, modifier, operation));
    }

    @SafeVarargs
    protected final void addDimension(double modifier, Operation operation, ResourceKey<Level>... dimensions) {
        for (ResourceKey<Level> dimension : dimensions) {
            addDimension(dimension.location(), dimension, modifier, operation);
        }
    }

    protected void addDimension(ResourceLocation location, ResourceKey<Level> dimension, double modifier, Operation operation) {
        add(new ResourceKeyFocusModifier<>(location, dimension, modifier, operation));
    }

    protected void add(double modifier, Operation operation, MobEffect... effects) {
        for (MobEffect effect : effects) {
            ResourceKey<MobEffect> resourceKey = BuiltInRegistries.MOB_EFFECT.getResourceKey(effect).orElseThrow();
            add(resourceKey.location(), resourceKey, modifier, operation);
        }
    }

    protected void add(ResourceLocation location, ResourceKey<MobEffect> effect, double modifier, Operation operation) {
        add(new ResourceKeyFocusModifier<>(location, effect, modifier, operation));
    }

    protected void add(EntityType<?> type, CompoundTag nbt, double modifier, Operation operation) {
        add(BuiltInRegistries.ENTITY_TYPE.getKey(type), type, nbt, modifier, operation);
    }

    protected void add(EntityType<?> type, double modifier, Operation operation) {
        add(BuiltInRegistries.ENTITY_TYPE.getKey(type), type, new CompoundTag(), modifier, operation);
    }

    protected void add(double modifier, Operation operation, EntityType<?>... types) {
        for (EntityType<?> type : types) {
            add(type, null, modifier, operation);
        }
    }

    protected void add(ResourceLocation location, EntityType<?> type, CompoundTag nbt, double modifier, Operation operation) {
        add(new EntityFocusModifier(location, type, nbt, modifier, operation));
    }

    protected void add(double modifier, Operation operation, ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            add(BuiltInRegistries.ITEM.getKey(stack.getItem()), stack, modifier, operation);
        }
    }

    protected void add(ResourceLocation location, ItemStack stack, double modifier, Operation operation) {
        add(new ItemStackFocusModifier(location, stack, null, modifier, operation));
    }

    protected void add(double modifier, Operation operation, Item... items) {
        for (Item item : items) {
            add(BuiltInRegistries.ITEM.getKey(item), item, modifier, operation);
        }
    }

    protected void add(ResourceLocation location, Item item, double modifier, Operation operation) {
        add(new ItemStackFocusModifier(location, ItemStack.EMPTY, item, modifier, operation));
    }

    @SafeVarargs
    protected final void addStructure(double modifier, Operation operation, ResourceKey<Structure>... structures) {
        for (ResourceKey<Structure> structure : structures) {
            addStructure(structure.location(), structure, modifier, operation);
        }
    }

    protected void addStructure(ResourceLocation location, ResourceKey<Structure> structure, double modifier, Operation operation) {
        add(new ResourceKeyFocusModifier<>(location, structure, modifier, operation));
    }

    @Override
    public String getName() {
        return "Focus Modifiers";
    }
}
