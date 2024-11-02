package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class MinejagoDamageTypeTags {
    public static final TagKey<DamageType> RESISTED_BY_GOLDEN_WEAPONS = create("resisted_by_golden_weapons");

    private static TagKey<DamageType> create(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, Minejago.modLoc(name));
    }
}
