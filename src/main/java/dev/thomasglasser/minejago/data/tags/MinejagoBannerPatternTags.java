package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BannerPatternTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoBannerPatternTags extends BannerPatternTagsProvider {
    public static final TagKey<BannerPattern> PATTERN_ITEM_FOUR_WEAPONS = TagKey.create(Registry.BANNER_PATTERN_REGISTRY, new ResourceLocation(Minejago.MODID, "pattern_item/four_weapons"));

    public MinejagoBannerPatternTags(DataGenerator p_236411_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_236411_, Minejago.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(PATTERN_ITEM_FOUR_WEAPONS)
                .add(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT.get())
                .add(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT.get());
        tag(BannerPatternTags.NO_ITEM_REQUIRED)
                .add(MinejagoBannerPatterns.EDGE_LINES.get());
    }
}