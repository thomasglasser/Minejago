package dev.thomasglasser.minejago.data.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MinejagoLootTables extends LootTableProvider
{
    public MinejagoLootTables(PackOutput packOutput) {
        super(packOutput, Set.of(), List.of(
                new SubProviderEntry(MinejagoChestLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(MinejagoBlockLoot::new, LootContextParamSets.BLOCK)));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((location, lootTable) -> lootTable.validate(validationtracker));
    }
}
