package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistryKeys;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MinejagoPowerTags
{
    public static final TagKey<Power> EARTH = create("earth");

    private static TagKey<Power> create(String name)
    {
        return TagKey.create(MinejagoRegistryKeys.POWER, Minejago.modLoc(name));
    }

}
