package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.dimension.DimensionType;

public class MinejagoDimensionTypeTags
{
	public static final TagKey<DimensionType> HAS_SKULKIN_RAIDS = create("has_skulkin_raids");

	private static TagKey<DimensionType> create(String name) {
		return TagKey.create(Registries.DIMENSION_TYPE, Minejago.modLoc(name));
	}
}
