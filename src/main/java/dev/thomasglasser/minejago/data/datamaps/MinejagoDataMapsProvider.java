package dev.thomasglasser.minejago.data.datamaps;

import dev.thomasglasser.minejago.datamaps.MinejagoDataMaps;
import dev.thomasglasser.minejago.datamaps.PotionDrainable;
import dev.thomasglasser.minejago.datamaps.PotionFillable;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
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
    protected void gather() {
        Builder<VibrationFrequency, GameEvent> vibrationBuilder = builder(NeoForgeDataMaps.VIBRATION_FREQUENCIES);
        MinejagoGameEvents.VIBRATION_FREQUENCY_FOR_EVENT.forEach((event, frequency) -> vibrationBuilder.add(event, new VibrationFrequency(frequency), false));

        builder(MinejagoDataMaps.POTION_FILLABLES)
                .add(Items.GLASS_BOTTLE.builtInRegistryHolder(), new PotionFillable(2, Items.POTION), false)
                .add(MinejagoItems.TEACUP, new PotionFillable(1, MinejagoItems.FILLED_TEACUP.get()), false);

        builder(MinejagoDataMaps.POTION_DRAINABLES)
                .add(MinejagoItems.FILLED_TEACUP, new PotionDrainable(1, MinejagoItems.TEACUP.toStack()), false)
                // TODO: Add milk bucket when lookupProvider is available
//                .add(Items.MILK_BUCKET.builtInRegistryHolder(), new PotionDrainable(MinejagoPotions.MILK.asReferenceFrom(lookupProvider), 6, Items.BUCKET.getDefaultInstance()), false)
                .add(Items.WATER_BUCKET.builtInRegistryHolder(), new PotionDrainable(Potions.WATER, 6, Items.BUCKET.getDefaultInstance()), false)
                .add(Items.POTION.builtInRegistryHolder(), new PotionDrainable(2, Items.GLASS_BOTTLE.getDefaultInstance()), false);
    }
}
