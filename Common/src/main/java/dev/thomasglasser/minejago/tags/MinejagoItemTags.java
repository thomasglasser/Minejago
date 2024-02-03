package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.tags.TommyLibItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MinejagoItemTags
{
    public static final TagKey<Item> GOLDEN_WEAPONS = create("golden_weapons");
    public static final TagKey<Item> TEAPOTS = create("teapots");
    public static final TagKey<Item> LECTERN_SCROLLS = create("lectern_scrolls");
    public static final TagKey<Item> SCROLL_SHELF_SCROLLS = create("scroll_shelf_scrolls");
    public static final TagKey<Item> DRAGON_FOODS = create("dragon_foods");
    public static final TagKey<Item> DRAGON_TREATS = create("dragon_treats");

    // Logs
    public static final TagKey<Item> ENCHANTED_LOGS = TommyLibItemTags.logs(MinejagoBlocks.ENCHANTED_WOOD_SET);

    private static TagKey<Item> create(String name)
    {
        return TagKey.create(Registries.ITEM, Minejago.modLoc(name));
    }
}
