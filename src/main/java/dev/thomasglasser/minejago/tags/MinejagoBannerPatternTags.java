package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class MinejagoBannerPatternTags {
    public static final TagKey<BannerPattern> PATTERN_ITEM_FOUR_WEAPONS = TagKey.create(Registries.BANNER_PATTERN, Minejago.modLoc("pattern_item/four_weapons"));
}