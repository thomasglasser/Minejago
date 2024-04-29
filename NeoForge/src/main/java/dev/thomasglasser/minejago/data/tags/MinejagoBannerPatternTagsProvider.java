package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraft.tags.BannerPatternTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinejagoBannerPatternTagsProvider extends BannerPatternTagsProvider
{
    public MinejagoBannerPatternTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, future, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS)
                .add(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT)
                .add(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT);
        tag(BannerPatternTags.NO_ITEM_REQUIRED)
                .add(MinejagoBannerPatterns.EDGE_LINES);
    }
}