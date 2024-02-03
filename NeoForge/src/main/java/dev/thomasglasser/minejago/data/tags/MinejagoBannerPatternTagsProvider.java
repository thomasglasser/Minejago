package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedBannerPatternTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BannerPatternTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinejagoBannerPatternTagsProvider extends ExtendedBannerPatternTagsProvider
{
    public MinejagoBannerPatternTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, future, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS)
                .add(modKey(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT.get()))
                .add(modKey(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT.get()));
        tag(BannerPatternTags.NO_ITEM_REQUIRED)
                .add(modKey(MinejagoBannerPatterns.EDGE_LINES.get()));
    }
}