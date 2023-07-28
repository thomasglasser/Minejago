package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BannerPatternTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinejagoBannerPatternTagsProvider extends BannerPatternTagsProvider {
    public MinejagoBannerPatternTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, future, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS)
                .add(modLoc(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT.get()))
                .add(modLoc(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT.get()));
        tag(BannerPatternTags.NO_ITEM_REQUIRED)
                .add(modLoc(MinejagoBannerPatterns.EDGE_LINES.get()));
    }

    private static ResourceKey<BannerPattern> modLoc(BannerPattern pattern)
    {
        return ResourceKey.create(Registries.BANNER_PATTERN, new ResourceLocation(Minejago.MOD_ID, pattern.getHashname()));
    }
}