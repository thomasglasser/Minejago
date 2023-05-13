package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class MinejagoBiomeTags
{
    public static final TagKey<Biome> HAS_FOUR_WEAPONS = create("has_four_weapons");

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, Minejago.modLoc(name));
    }
}
