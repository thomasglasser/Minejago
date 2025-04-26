package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MinejagoItemTags {
    public static final TagKey<Item> GOLDEN_WEAPONS = create("golden_weapons");
    public static final TagKey<Item> GOLDEN_WEAPON_HOLDERS = create("golden_weapon_holders");
    public static final TagKey<Item> TEAPOTS = create("teapots");
    public static final TagKey<Item> LECTERN_SCROLLS = create("lectern_scrolls");
    public static final TagKey<Item> SCROLL_SHELF_SCROLLS = create("scroll_shelf_scrolls");
    public static final TagKey<Item> DRAGON_FOODS = create("dragon_foods");
    public static final TagKey<Item> DRAGON_TREATS = create("dragon_treats");
    public static final TagKey<Item> EARTH_DRAGON_PROTECTS = create("earth_dragon_protects");
    public static final TagKey<Item> REPAIRS_SKELETAL_ARMOR = create("repairs_skeletal_armor");
    public static final TagKey<Item> BONE_TOOL_MATERIALS = create("bone_tool_materials");
    public static final TagKey<Item> GI = create("gi");
    public static final TagKey<Item> TEACUPS = create("teacups");

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, Minejago.modLoc(name));
    }
}
