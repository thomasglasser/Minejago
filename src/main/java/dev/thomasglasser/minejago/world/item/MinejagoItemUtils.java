package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.tags.MinejagoStructureTags;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorationTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public final class MinejagoItemUtils {
    public static final String NO_STRUCTURES_FOUND = "item.minejago.golden_weapons_map.no_structures_found";
    private static final byte MAP_SCALE = 6;
    private static final int RADIUS = 100;

    private MinejagoItemUtils() {}

    public static ItemStack fillTeacup(Holder<Potion> potion) {
        return PotionContents.createItemStack(MinejagoItems.FILLED_TEACUP.get(), potion);
    }

    public static ItemStack createGoldenWeaponsMap(ServerLevel serverLevel, Entity entity, TagKey<Structure> structure, Holder<MapDecorationType> decoration) {
        BlockPos pos = serverLevel.findNearestMapStructure(structure, entity.blockPosition(), RADIUS, true);
        if (pos == null) {
            if (entity instanceof Player player)
                player.displayClientMessage(Component.translatable(NO_STRUCTURES_FOUND), true);
            return ItemStack.EMPTY;
        }
        ItemStack map = newMap(serverLevel, pos);
        MapItemSavedData.addTargetDecoration(map, pos, "structure", decoration);
        map.set(MinejagoDataComponents.GOLDEN_WEAPONS_MAP.get(), Unit.INSTANCE);
        return map;
    }

    public static ItemStack createCaveOfDespairMap(ServerLevel serverLevel, Entity entity) {
        return createGoldenWeaponsMap(serverLevel, entity, MinejagoStructureTags.CAVE_OF_DESPAIR, MinejagoMapDecorationTypes.SCYTHE_OF_QUAKES.asReferenceFrom(serverLevel.registryAccess()));
    }

    private static ItemStack newMap(ServerLevel serverLevel, BlockPos structurePos) {
        ItemStack map = MapItem.create(serverLevel, structurePos.getX(), structurePos.getZ(), MAP_SCALE, true, true);
        MapItem.renderBiomePreviewMap(serverLevel, map);
        return map;
    }
}
