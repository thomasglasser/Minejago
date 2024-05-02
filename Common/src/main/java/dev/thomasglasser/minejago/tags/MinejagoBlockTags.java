package dev.thomasglasser.minejago.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.world.level.block.WoodSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class MinejagoBlockTags
{
    public static final TagKey<Block> MINEABLE_WITH_SCYTHE = create("mineable/scythe");

    public static final TagKey<Block> TEAPOTS = create("teapots");
    public static final TagKey<Block> SHURIKEN_BREAKS = create("shuriken_breaks");

    // Wood sets
    public static final TagKey<Block> ENCHANTED_LOGS = logs(MinejagoBlocks.ENCHANTED_WOOD_SET.get());

    public static TagKey<Block> logs(WoodSet set)
    {
        return create(new ResourceLocation(set.id().getNamespace(), set.id().getPath() + "_logs"));
    }

    private static TagKey<Block> create(String name)
    {
        return TagKey.create(Registries.BLOCK, Minejago.modLoc(name));
    }
    public static TagKey<Block> create(ResourceLocation name)
    {
        return TagKey.create(Registries.BLOCK, name);
    }
}
