package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MinejagoBlockTags
{
    public static final TagKey<Block> MINEABLE_WITH_SCYTHE = create("mineable/scythe");

    public static final TagKey<Block> TEAPOTS = create("teapots");
    public static final TagKey<Block> SHURIKEN_BREAKS = create("shuriken_breaks");

    // Logs
    public static final TagKey<Block> ENCHANTED_LOGS = create("enchanted_logs");

    private static TagKey<Block> create(String name)
    {
        return TagKey.create(Registries.BLOCK, Minejago.modLoc(name));
    }
    public static TagKey<Block> create(ResourceLocation name)
    {
        return TagKey.create(Registries.BLOCK, name);
    }
}
