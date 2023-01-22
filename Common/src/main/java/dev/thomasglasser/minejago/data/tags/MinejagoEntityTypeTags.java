package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class MinejagoEntityTypeTags
{
    public static final TagKey<EntityType<?>> SKULKINS = create("skulkins");

    private static TagKey<EntityType<?>> create(String name)
    {
        return TagKey.create(Registries.ENTITY_TYPE, Minejago.modLoc(name));
    }

}
