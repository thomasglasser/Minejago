package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoStructureTags;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorations;
import net.mehvahdjukaar.moonlight.api.map.MapHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EmptyMapItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import java.util.function.BiFunction;

public class CustomEmptyMapItem extends EmptyMapItem {
    protected BiFunction<ServerLevel, Entity, ItemStack> fillFunction;

    public CustomEmptyMapItem(BiFunction<ServerLevel, Entity, ItemStack> fillFunction, Properties properties) {
        super(properties);
        this.fillFunction = fillFunction;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (level.isClientSide) {
            return InteractionResultHolder.success(itemStack);
        } else {
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            player.level().playSound(null, player, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, player.getSoundSource(), 1.0F, 1.0F);
            ItemStack itemStack2 = fillFunction.apply((ServerLevel) level, player);
            if (itemStack.isEmpty()) {
                return InteractionResultHolder.consume(itemStack2);
            } else {
                if (!player.getInventory().add(itemStack2.copy())) {
                    player.drop(itemStack2, false);
                }

                return InteractionResultHolder.consume(itemStack);
            }
        }
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
            if (pos1 != null) MapHelper.addDecorationToMap(itemstack, pos1, MinejagoMapDecorations.NUNCHUCKS, -1);
            if (pos2 != null) MapHelper.addDecorationToMap(itemstack, pos2, MinejagoMapDecorations.SWORD, -1);
            if (earth != null) MapHelper.addDecorationToMap(itemstack, earth, MinejagoMapDecorations.SCYTHE, -1);
            if (pos4 != null) MapHelper.addDecorationToMap(itemstack, pos4, MinejagoMapDecorations.SHURIKENS, -1);
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
