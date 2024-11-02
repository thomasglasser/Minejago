package dev.thomasglasser.minejago.datamaps;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class MinejagoDataMaps {
    public static final DataMapType<Item, PotionFillable> POTION_FILLABLES = DataMapType.builder(
            Minejago.modLoc("potion_fillables"), Registries.ITEM, PotionFillable.CODEC)
            .synced(PotionFillable.CODEC, true)
            .build();

    public static final DataMapType<Item, PotionDrainable> POTION_DRAINABLES = DataMapType.builder(
            Minejago.modLoc("potion_drainables"), Registries.ITEM, PotionDrainable.CODEC)
            .synced(PotionDrainable.CODEC, true)
            .build();
}
