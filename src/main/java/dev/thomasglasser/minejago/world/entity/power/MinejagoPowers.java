package dev.thomasglasser.minejago.world.entity.power;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.resources.ResourceKey;

public class MinejagoPowers {
    public static final ResourceKey<Power> NONE = create("none");
    public static final ResourceKey<Power> ICE = create("ice");
    public static final ResourceKey<Power> EARTH = create("earth");
    public static final ResourceKey<Power> FIRE = create("fire");
    public static final ResourceKey<Power> LIGHTNING = create("lightning");
    public static final ResourceKey<Power> CREATION = create("creation");

    private static ResourceKey<Power> create(String id) {
        return ResourceKey.create(MinejagoRegistries.POWER, Minejago.modLoc(id));
    }

    public static void init() {}
}
