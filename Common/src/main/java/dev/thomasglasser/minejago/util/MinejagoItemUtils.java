package dev.thomasglasser.minejago.util;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

public final class MinejagoItemUtils
{
    private MinejagoItemUtils() {}

    public static void safeShrink(int d, ItemStack item, Player player)
    {
        if (!player.getAbilities().instabuild) item.shrink(d);
    }

    public static ItemStack fillTeacup(Potion potion)
    {
        return PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), potion);
    }
}
