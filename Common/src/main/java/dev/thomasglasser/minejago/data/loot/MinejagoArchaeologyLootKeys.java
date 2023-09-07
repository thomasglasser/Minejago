package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;

public class MinejagoArchaeologyLootKeys
{
    public static final ResourceLocation CAVE_OF_DESPAIR = modLoc("cave_of_despair");

    private static ResourceLocation modLoc(String name) {
        return new ResourceLocation(Minejago.MOD_ID, "archaeology/" + name);
    }
}
