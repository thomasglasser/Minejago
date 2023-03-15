package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraftforge.common.data.ExistingFileHelper;
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
                .add(MinejagoPaintingVariants.A_MORNING_BREW.getResourceKey())
                .add(MinejagoPaintingVariants.AMBUSHED.getResourceKey())
                .add(MinejagoPaintingVariants.BEFORE_THE_STORM.getResourceKey())
                .add(MinejagoPaintingVariants.CREATION.getResourceKey())
                .add(MinejagoPaintingVariants.EARTH.getResourceKey())
                .add(MinejagoPaintingVariants.FIRE.getResourceKey())
                .add(MinejagoPaintingVariants.FRUIT_COLORED_NINJA.getResourceKey())
                .add(MinejagoPaintingVariants.ICE.getResourceKey())
                .add(MinejagoPaintingVariants.LIGHTNING.getResourceKey())
                .add(MinejagoPaintingVariants.NEEDS_HAIR_GEL.getResourceKey())
                .add(MinejagoPaintingVariants.NOT_FOR_FURNITURE.getResourceKey());
    }
}
