package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinejagoPaintingVariantTagsProvider extends PaintingVariantTagsProvider
{
    public MinejagoPaintingVariantTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(PaintingVariantTags.PLACEABLE)
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.A_MORNING_BREW.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.AMBUSHED.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.BEFORE_THE_STORM.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.CREATION.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.EARTH.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.FIRE.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.FRUIT_COLORED_NINJA.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.ICE.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.LIGHTNING.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.NEEDS_HAIR_GEL.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.NOT_FOR_FURNITURE.get()).orElseThrow())
                .add(BuiltInRegistries.PAINTING_VARIANT.getResourceKey(MinejagoPaintingVariants.IT_TAKES_A_VILLAGE.get()).orElseThrow());
    }
}
