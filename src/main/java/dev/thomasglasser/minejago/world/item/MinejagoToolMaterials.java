package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

public interface MinejagoToolMaterials {
    ToolMaterial BONE = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 150, 10.0F, 1.5F, 10, MinejagoItemTags.BONE_TOOL_MATERIALS);

    static void init() {}
}
