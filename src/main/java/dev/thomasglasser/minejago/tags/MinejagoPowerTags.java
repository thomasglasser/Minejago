package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.tags.TagKey;

public class MinejagoPowerTags {
    public static final TagKey<Power> EARTH = create("earth");

    private static TagKey<Power> create(String name) {
        return TagKey.create(MinejagoRegistries.POWER, Minejago.modLoc(name));
    }
}
