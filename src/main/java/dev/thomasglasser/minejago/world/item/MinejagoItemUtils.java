package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.tags.MinejagoStructureTags;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorationTypes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.neoforged.neoforge.common.Tags;

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
                player.displayClientMessage(Component.translatable(NO_STRUCTURES_FOUND, Component.translatable(Tags.getTagTranslationKey(structure))), true);
            return ItemStack.EMPTY;
        }
        ItemStack map = newMap(serverLevel, pos);
        MapItemSavedData.addTargetDecoration(map, pos, "structure", decoration);
        map.set(MinejagoDataComponents.GOLDEN_WEAPONS_MAP.get(), Unit.INSTANCE);
        map.set(DataComponents.CUSTOM_NAME, Component.translatable(Tags.getTagTranslationKey(structure)));
        return map;
    }

    public static ItemStack createFourWeaponsMaps(ServerLevel serverLevel, Entity entity) {
        ItemStack bundle = Items.BUNDLE.getDefaultInstance();
        List<ItemStack> maps = new ArrayList<>();
        maps.add(createGoldenWeaponsMap(serverLevel, entity, MinejagoStructureTags.FOUR_WEAPONS, MapDecorationTypes.RED_BANNER/*TODO: Four Weapons icon*/));
        maps.add(createGoldenWeaponsMap(serverLevel, entity, MinejagoStructureTags.NINJAGO_CITY, MapDecorationTypes.GRAY_BANNER/*TODO: Ninjago City icon*/));
        maps.add(createGoldenWeaponsMap(serverLevel, entity, MinejagoStructureTags.MONASTERY_OF_SPINJITZU, MapDecorationTypes.WHITE_BANNER/*TODO: Monastery icon*/));
        maps.add(createGoldenWeaponsMap(serverLevel, entity, MinejagoStructureTags.CAVE_OF_DESPAIR, MinejagoMapDecorationTypes.SCYTHE_OF_QUAKES.asReferenceFrom(serverLevel.registryAccess())));
        // TODO: Add other structures
//        maps.add(createGoldenWeaponsMap(serverLevel, entity, MinejagoStructureTags.ICE_TEMPLE, MinejagoMapDecorationTypes.SHURIKENS_OF_ICE.asReferenceFrom(serverLevel.registryAccess())));
//        maps.add(createGoldenWeaponsMap(serverLevel, entity, MinejagoStructureTags.FLOATING_RUINS, MinejagoMapDecorationTypes.NUNCHUCKS_OF_LIGHTNING.asReferenceFrom(serverLevel.registryAccess())));
//        maps.add(createGoldenWeaponsMap(serverLevel, entity, MinejagoStructureTags.FIRE_TEMPLE, MinejagoMapDecorationTypes.SWORD_OF_FIRE.asReferenceFrom(serverLevel.registryAccess())));
        maps.removeIf(ItemStack::isEmpty);
        BundleContents contents = new BundleContents(maps);
        bundle.set(DataComponents.BUNDLE_CONTENTS, contents);
        bundle.set(MinejagoDataComponents.GOLDEN_WEAPONS_MAP.get(), Unit.INSTANCE);
        return bundle;
    }

    private static ItemStack newMap(ServerLevel serverLevel, BlockPos structurePos) {
        ItemStack map = MapItem.create(serverLevel, structurePos.getX(), structurePos.getZ(), MAP_SCALE, true, true);
        MapItem.renderBiomePreviewMap(serverLevel, map);
        return map;
    }
}
