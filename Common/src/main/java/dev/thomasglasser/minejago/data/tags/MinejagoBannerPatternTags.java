package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class MinejagoBannerPatternTags {
    public static final TagKey<BannerPattern> PATTERN_ITEM_FOUR_WEAPONS = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(Minejago.MOD_ID, "pattern_item/four_weapons"));
}