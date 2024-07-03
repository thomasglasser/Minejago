package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.tags.MinejagoStructureTags;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorationTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public final class MinejagoItemUtils {
    private MinejagoItemUtils() {}

    public static ItemStack fillTeacup(Holder<Potion> potion) {
        return PotionContents.createItemStack(MinejagoItems.FILLED_TEACUP.get(), potion);
    }

    public static ItemStack createGoldenWeaponsMap(ServerLevel serverLevel, Entity entity) {
        ItemStack itemstack = MapItem.create(serverLevel, (int) entity.getX(), (int) entity.getZ(), (byte) 4, true, true);
        MapItem.renderBiomePreviewMap(serverLevel, itemstack);
        /* TODO: Find lightning */ BlockPos pos1 = serverLevel.findNearestMapStructure(StructureTags.VILLAGE, entity.getOnPos(), Integer.MAX_VALUE, false);
        /* TODO: Find fire */ BlockPos pos2 = serverLevel.findNearestMapStructure(StructureTags.RUINED_PORTAL, entity.getOnPos(), Integer.MAX_VALUE, false);
        BlockPos earth = serverLevel.findNearestMapStructure(MinejagoStructureTags.CAVE_OF_DESPAIR, entity.getOnPos(), Integer.MAX_VALUE, false);
        /* TODO: Find ice */ BlockPos pos4 = serverLevel.findNearestMapStructure(StructureTags.MINESHAFT, entity.getOnPos(), Integer.MAX_VALUE, false);
        if (pos1 != null) MapItemSavedData.addTargetDecoration(itemstack, pos1, "lightning", serverLevel.holderOrThrow(MinejagoMapDecorationTypes.NUNCHUCKS_OF_LIGHTNING.getKey()));
        if (pos2 != null) MapItemSavedData.addTargetDecoration(itemstack, pos2, "fire", serverLevel.holderOrThrow(MinejagoMapDecorationTypes.SWORD_OF_FIRE.getKey()));
        if (earth != null) MapItemSavedData.addTargetDecoration(itemstack, earth, "earth", serverLevel.holderOrThrow(MinejagoMapDecorationTypes.SCYTHE_OF_QUAKES.getKey()));
        if (pos4 != null) MapItemSavedData.addTargetDecoration(itemstack, pos4, "ice", serverLevel.holderOrThrow(MinejagoMapDecorationTypes.SHURIKENS_OF_ICE.getKey()));
        itemstack.set(MinejagoDataComponents.GOLDEN_WEAPONS_MAP.get(), Unit.INSTANCE);
        return itemstack;
    }

    public static byte getLoyaltyFromItem(ItemStack stack, Level level, Entity entity) {
        return level instanceof ServerLevel serverlevel
                ? (byte) Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, entity), 0, 127)
                : 0;
    }
}
