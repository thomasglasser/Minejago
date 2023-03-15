package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MinejagoItemTags
{
    // Loader tags
    public static final TagKey<Item> WOODEN_RODS = create("wooden_rods");
    public static final TagKey<Item> IRON_INGOTS = create("iron_ingots");

    public static final TagKey<Item> GOLDEN_WEAPONS = create("golden_weapons");

    private static TagKey<Item> create(String name)
    {
        return TagKey.create(Registries.ITEM, Minejago.modLoc(name));
    }
}
