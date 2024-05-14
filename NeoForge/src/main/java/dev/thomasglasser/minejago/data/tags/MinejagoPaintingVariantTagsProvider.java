package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import net.minecraft.core.HolderLookup;
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
                .add(MinejagoPaintingVariants.A_MORNING_BREW.getKey())
                .add(MinejagoPaintingVariants.AMBUSHED.getKey())
                .add(MinejagoPaintingVariants.BEFORE_THE_STORM.getKey())
                .add(MinejagoPaintingVariants.CREATION.getKey())
                .add(MinejagoPaintingVariants.EARTH.getKey())
                .add(MinejagoPaintingVariants.FIRE.getKey())
                .add(MinejagoPaintingVariants.FRUIT_COLORED_NINJA.getKey())
                .add(MinejagoPaintingVariants.ICE.getKey())
                .add(MinejagoPaintingVariants.LIGHTNING.getKey())
                .add(MinejagoPaintingVariants.NEEDS_HAIR_GEL.getKey())
                .add(MinejagoPaintingVariants.NOT_FOR_FURNITURE.getKey())
                .add(MinejagoPaintingVariants.IT_TAKES_A_VILLAGE.getKey());
    }
}
