package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.WoodSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class MinejagoItemTags
{
    // Loader tags
    public static final TagKey<Item> WOODEN_RODS = create("wooden_rods");
    public static final TagKey<Item> IRON_INGOTS = create("iron_ingots");
    public static final TagKey<Item> MEATS = create("meats");
    public static final Map<DyeColor, TagKey<Item>> DYES_MAP = dyesMap();

    public static final TagKey<Item> GOLDEN_WEAPONS = create("golden_weapons");
    public static final TagKey<Item> TEAPOTS = create("teapots");
    public static final TagKey<Item> LECTERN_SCROLLS = create("lectern_scrolls");
    public static final TagKey<Item> SCROLL_SHELF_SCROLLS = create("scroll_shelf_scrolls");
    public static final TagKey<Item> DRAGON_FOODS = create("dragon_foods");
    public static final TagKey<Item> DRAGON_TREATS = create("dragon_treats");

    // Logs
    public static final TagKey<Item> ENCHANTED_LOGS = logs(MinejagoBlocks.ENCHANTED_WOOD_SET);

    private static TagKey<Item> create(String name)
    {
        return TagKey.create(Registries.ITEM, Minejago.modLoc(name));
    }
    public static TagKey<Item> create(ResourceLocation name)
    {
        return TagKey.create(Registries.ITEM, name);
    }
    private static Map<DyeColor, TagKey<Item>> dyesMap()
    {
        Map<DyeColor, TagKey<Item>> map = new HashMap<>();
        for (DyeColor color : DyeColor.values())
        {
            map.put(color, create(color.getName() + "_dyes"));
        }
        return map;
    }
    public static TagKey<Item> logs(WoodSet set)
    {
        return create(new ResourceLocation(set.id().getNamespace(), set.id().getPath() + "_logs"));
    }
}
