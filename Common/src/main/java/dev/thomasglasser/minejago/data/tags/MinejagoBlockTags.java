package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MinejagoBlockTags
{
    public static final TagKey<Block> UNBREAKABLE = create("unbreakable");

    private static TagKey<Block> create(String name)
    {
        return TagKey.create(Registries.BLOCK, Minejago.modLoc(name));
    }
}
