package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BannerPatternTags;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoBannerPatternTagsProvider extends ExtendedTagsProvider<BannerPattern> {
    public MinejagoBannerPatternTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.BANNER_PATTERN, future, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS)
                .add(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT)
                .add(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT);
        tag(MinejagoBannerPatternTags.PATTERN_ITEM_NINJA)
                .add(MinejagoBannerPatterns.NINJA);
        tag(BannerPatternTags.NO_ITEM_REQUIRED)
                .add(MinejagoBannerPatterns.EDGE_LINES);
    }
}
