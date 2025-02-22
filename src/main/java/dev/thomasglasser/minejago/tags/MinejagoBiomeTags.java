package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class MinejagoBiomeTags {
    public static final TagKey<Biome> HAS_FOCUS_TREES = create("has_focus_trees");
    public static final TagKey<Biome> HAS_FOUR_WEAPONS = create("has_four_weapons");
    public static final TagKey<Biome> HAS_CAVE_OF_DESPAIR = create("has_cave_of_despair");
    public static final TagKey<Biome> HAS_NINJAGO_CITY = create("has_ninjago_city");
    public static final TagKey<Biome> HAS_MONASTERY_OF_SPINJITZU = create("has_monastery_of_spinjitzu");

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, Minejago.modLoc(name));
    }
}
