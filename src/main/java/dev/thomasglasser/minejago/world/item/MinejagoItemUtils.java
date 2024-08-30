package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.tags.MinejagoStructureTags;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorationTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public final class MinejagoItemUtils {
    private MinejagoItemUtils() {}

    public static ItemStack fillTeacup(Holder<Potion> potion) {
        return PotionContents.createItemStack(MinejagoItems.FILLED_TEACUP.get(), potion);
    }

    public static ItemStack createGoldenWeaponsMap(ServerLevel serverLevel, Entity entity) {
        ItemStack map = MapItem.create(serverLevel, (int) entity.getX(), (int) entity.getZ(), (byte) 7, true, true);
        MapItem.renderBiomePreviewMap(serverLevel, map);
        BlockPos earth = serverLevel.findNearestMapStructure(MinejagoStructureTags.CAVE_OF_DESPAIR, entity.getOnPos(), Integer.MAX_VALUE, false);
        // TODO: Add other structures
//        BlockPos ice = serverLevel.findNearestMapStructure(StructureTags.MINESHAFT, entity.getOnPos(), Integer.MAX_VALUE, false);
//        BlockPos lightning = serverLevel.findNearestMapStructure(StructureTags.VILLAGE, entity.getOnPos(), Integer.MAX_VALUE, false);
//        BlockPos fire = serverLevel.findNearestMapStructure(StructureTags.RUINED_PORTAL, entity.getOnPos(), Integer.MAX_VALUE, false);
        if (earth != null)
            MapItemSavedData.addTargetDecoration(map, earth, "earth", serverLevel.holderOrThrow(MinejagoMapDecorationTypes.SCYTHE_OF_QUAKES.getKey()));
//        if (ice != null) MapItemSavedData.addTargetDecoration(map, ice, "ice", serverLevel.holderOrThrow(MinejagoMapDecorationTypes.SHURIKENS_OF_ICE.getKey()));
//        if (lightning != null) MapItemSavedData.addTargetDecoration(map, lightning, "lightning", serverLevel.holderOrThrow(MinejagoMapDecorationTypes.NUNCHUCKS_OF_LIGHTNING.getKey()));
//        if (fire != null) MapItemSavedData.addTargetDecoration(map, fire, "fire", serverLevel.holderOrThrow(MinejagoMapDecorationTypes.SWORD_OF_FIRE.getKey()));
        map.set(MinejagoDataComponents.GOLDEN_WEAPONS_MAP.get(), Unit.INSTANCE);
        return map;
    }
}
