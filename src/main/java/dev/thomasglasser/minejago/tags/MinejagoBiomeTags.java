package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class MinejagoBiomeTags
{
    public static final TagKey<Biome> HAS_FOUR_WEAPONS = create("has_four_weapons");
    public static final TagKey<Biome> HAS_CAVE_OF_DESPAIR = create("has_cave_of_despair");
    public static final TagKey<Biome> WITHOUT_SKULKIN_PATROL_SPAWNS = create("without_skulkin_patrol_spawns");
    public static final TagKey<Biome> HAS_FOCUS_TREES = create("has_focus_trees");
    public static final TagKey<Biome> HAS_NINJAGO_CITY = create("has_ninjago_city");

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, Minejago.modLoc(name));
    }
}
