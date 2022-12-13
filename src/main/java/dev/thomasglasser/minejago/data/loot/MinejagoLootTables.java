package dev.thomasglasser.minejago.data.loot;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MinejagoLootTables extends LootTableProvider
{
    public MinejagoLootTables(PackOutput packOutput) {
        super(packOutput, Set.of(new ResourceLocation(Minejago.MOD_ID, "chests/four_weapons"), MinejagoBlocks.TEAPOT.get().getLootTable()), List.of(
                new LootTableProvider.SubProviderEntry(MinejagoChestLoot::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(MinejagoBlockLoot::new, LootContextParamSets.BLOCK)));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((location, lootTable) -> LootTables.validate(validationtracker, location, lootTable));
    }
}
