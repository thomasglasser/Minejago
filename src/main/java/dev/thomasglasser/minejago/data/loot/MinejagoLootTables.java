package dev.thomasglasser.minejago.data.loot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class MinejagoLootTables extends LootTableProvider {
    public MinejagoLootTables(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, Set.of(), List.of(
                new SubProviderEntry(MinejagoChestLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(MinejagoArchaeologyLoot::new, LootContextParamSets.ARCHAEOLOGY),
                new SubProviderEntry(MinejagoBlockLoot::new, LootContextParamSets.BLOCK)), provider);
    }
}
