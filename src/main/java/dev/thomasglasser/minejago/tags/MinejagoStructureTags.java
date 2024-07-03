package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public class MinejagoStructureTags {
    public static final TagKey<Structure> CAVE_OF_DESPAIR = create("cave_of_despair");
    public static final TagKey<Structure> HAS_GOLDEN_WEAPON = create("has_golden_weapon");

    private static TagKey<Structure> create(String name) {
        return TagKey.create(Registries.STRUCTURE, Minejago.modLoc(name));
    }
}
