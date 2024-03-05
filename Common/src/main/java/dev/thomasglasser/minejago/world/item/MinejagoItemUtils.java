package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoStructureTags;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorations;
import net.mehvahdjukaar.moonlight.api.map.MapHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public final class MinejagoItemUtils
{
    private MinejagoItemUtils() {}

    public static ItemStack fillTeacup(Potion potion)
    {
        return PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), potion);
    }

    public static ItemStack getFourWeaponsMap(ServerLevel serverLevel, Entity entity)
    {
        ItemStack itemstack = MapItem.create(serverLevel, (int)entity.getX(), (int)entity.getZ(), (byte)4, true, true);
        MapItem.renderBiomePreviewMap(serverLevel, itemstack);
        /* TODO: Find lightning */ BlockPos pos1 = serverLevel.findNearestMapStructure(StructureTags.VILLAGE, entity.getOnPos(), Integer.MAX_VALUE, false);
        /* TODO: Find fire */ BlockPos pos2 = serverLevel.findNearestMapStructure(StructureTags.RUINED_PORTAL, entity.getOnPos(), Integer.MAX_VALUE, false);
        BlockPos earth = serverLevel.findNearestMapStructure(MinejagoStructureTags.CAVE_OF_DESPAIR, entity.getOnPos(), Integer.MAX_VALUE, false);
        /* TODO: Find ice */ BlockPos pos4 = serverLevel.findNearestMapStructure(StructureTags.MINESHAFT, entity.getOnPos(), Integer.MAX_VALUE, false);
        if (Minejago.Dependencies.MOONLIGHT_LIB.isInstalled())
        {
            if (pos1 != null) MapHelper.addDecorationToStack(itemstack, pos1, MinejagoMapDecorations.NUNCHUCKS, -1);
            if (pos2 != null) MapHelper.addDecorationToStack(itemstack, pos2, MinejagoMapDecorations.SWORD, -1);
            if (earth != null) MapHelper.addDecorationToStack(itemstack, earth, MinejagoMapDecorations.SCYTHE, -1);
            if (pos4 != null) MapHelper.addDecorationToStack(itemstack, pos4, MinejagoMapDecorations.SHURIKENS, -1);
        }
        else
        {
            if (pos1 != null) MapItemSavedData.addTargetDecoration(itemstack, pos1, "lightning", MapDecoration.Type.BANNER_BLUE);
            if (pos2 != null) MapItemSavedData.addTargetDecoration(itemstack, pos2, "fire", MapDecoration.Type.BANNER_RED);
            if (earth != null) MapItemSavedData.addTargetDecoration(itemstack, earth, "earth", MapDecoration.Type.BANNER_BROWN);
            if (pos4 != null) MapItemSavedData.addTargetDecoration(itemstack, pos4, "ice", MapDecoration.Type.BANNER_WHITE);
        }
        itemstack.getOrCreateTag().putBoolean("IsFourWeaponsMap", true);
        return itemstack;
    }
}
