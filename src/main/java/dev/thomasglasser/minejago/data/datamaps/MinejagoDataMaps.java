package dev.thomasglasser.minejago.data.datamaps;

import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.VibrationFrequency;

public class MinejagoDataMaps extends DataMapProvider {
    /**
     * Create a new provider.
     *
     * @param packOutput     the output location
     * @param lookupProvider a {@linkplain CompletableFuture} supplying the registries
     */
    public MinejagoDataMaps(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        Builder<VibrationFrequency, GameEvent> vibrationBuilder = builder(NeoForgeDataMaps.VIBRATION_FREQUENCIES);
        MinejagoGameEvents.VIBRATION_FREQUENCY_FOR_EVENT.forEach((event, frequency) -> vibrationBuilder.add(event, new VibrationFrequency(frequency), false));
    }
}
