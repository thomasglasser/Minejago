package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.tags.TagKey;

public class MinejagoPowerTags {
    public static final TagKey<Power> CAN_USE_SCYTHE_OF_QUAKES = create("can_use_scythe_of_quakes");

    private static TagKey<Power> create(String name) {
        return TagKey.create(MinejagoRegistries.POWER, Minejago.modLoc(name));
    }
}
