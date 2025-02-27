package dev.thomasglasser.minejago.data.datamaps;

import dev.thomasglasser.minejago.datamaps.MinejagoDataMaps;
import dev.thomasglasser.minejago.datamaps.PotionDrainable;
import dev.thomasglasser.minejago.datamaps.PotionFillable;
import dev.thomasglasser.minejago.world.item.FilledTeacupItem;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.TeacupItem;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.VibrationFrequency;

public class MinejagoDataMapsProvider extends DataMapProvider {
    /**
     * Create a new provider.
     *
     * @param packOutput     the output location
     * @param lookupProvider a {@linkplain CompletableFuture} supplying the registries
     */
    public MinejagoDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        Builder<VibrationFrequency, GameEvent> vibrationBuilder = builder(NeoForgeDataMaps.VIBRATION_FREQUENCIES);
        MinejagoGameEvents.VIBRATION_FREQUENCY_FOR_EVENT.forEach((event, frequency) -> vibrationBuilder.add(event, new VibrationFrequency(frequency), false));

        Builder<PotionFillable, Item> fillables = builder(MinejagoDataMaps.POTION_FILLABLES)
                .add(Items.GLASS_BOTTLE.builtInRegistryHolder(), new PotionFillable(2, Items.POTION), false)
                .add(MinejagoItems.TEACUP, new PotionFillable(1, MinejagoItems.FILLED_TEACUP.get()), false)
                .add(MinejagoItems.MINICUP, new PotionFillable(1, MinejagoItems.FILLED_MINICUP.get()), false);
        for (Map.Entry<DyeColor, DeferredItem<TeacupItem>> teacup : MinejagoItems.TEACUPS.entrySet()) {
            fillables.add(teacup.getValue(), new PotionFillable(1, MinejagoItems.FILLED_TEACUPS.get(teacup.getKey()).get()), false);
        }

        Builder<PotionDrainable, Item> drainables = builder(MinejagoDataMaps.POTION_DRAINABLES)
                .add(Items.MILK_BUCKET.builtInRegistryHolder(), new PotionDrainable(MinejagoPotions.MILK.asReferenceFrom(provider), 6, Items.BUCKET.getDefaultInstance()), false)
                .add(Items.WATER_BUCKET.builtInRegistryHolder(), new PotionDrainable(Potions.WATER, 6, Items.BUCKET.getDefaultInstance()), false)
                .add(Items.POTION.builtInRegistryHolder(), new PotionDrainable(2, Items.GLASS_BOTTLE.getDefaultInstance()), false)
                .add(MinejagoItems.FILLED_TEACUP, new PotionDrainable(1, MinejagoItems.TEACUP.toStack()), false)
                .add(MinejagoItems.FILLED_MINICUP, new PotionDrainable(1, MinejagoItems.MINICUP.toStack()), false);
        for (Map.Entry<DyeColor, DeferredItem<FilledTeacupItem>> teacup : MinejagoItems.FILLED_TEACUPS.entrySet()) {
            drainables.add(teacup.getValue(), new PotionDrainable(1, MinejagoItems.TEACUPS.get(teacup.getKey()).toStack()), false);
        }
    }
}
