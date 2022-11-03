package dev.thomasglasser.minejago.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class MinejagoItemUtils
{
    private MinejagoItemUtils() {}

    public static void safeShrink(int d, ItemStack item, Player player)
    {
        if (!player.getAbilities().instabuild) item.shrink(d);
    }
}
