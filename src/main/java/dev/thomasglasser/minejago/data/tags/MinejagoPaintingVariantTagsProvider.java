package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoPaintingVariantTagsProvider extends ExtendedTagsProvider<PaintingVariant> {
    public MinejagoPaintingVariantTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.PAINTING_VARIANT, pProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(PaintingVariantTags.PLACEABLE)
                .add(MinejagoPaintingVariants.A_MORNING_BREW)
                .add(MinejagoPaintingVariants.AMBUSHED)
                .add(MinejagoPaintingVariants.BEFORE_THE_STORM)
                .add(MinejagoPaintingVariants.CREATION)
                .add(MinejagoPaintingVariants.EARTH)
                .add(MinejagoPaintingVariants.FIRE)
                .add(MinejagoPaintingVariants.FRUIT_COLORED_NINJA)
                .add(MinejagoPaintingVariants.ICE)
                .add(MinejagoPaintingVariants.LIGHTNING)
                .add(MinejagoPaintingVariants.NEEDS_HAIR_GEL)
                .add(MinejagoPaintingVariants.NOT_FOR_FURNITURE)
                .add(MinejagoPaintingVariants.IT_TAKES_A_VILLAGE);
    }
}
