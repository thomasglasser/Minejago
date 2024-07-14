package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.data.advancements.ExtendedAdvancementGenerator;
import java.util.Map;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MinejagoHusbandryAdvancements extends ExtendedAdvancementGenerator {
    public MinejagoHusbandryAdvancements(LanguageProvider enUs) {
        super(Minejago.MOD_ID, "husbandry", enUs);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        AdvancementHolder harvestWithScythe = create(ResourceLocation.withDefaultNamespace("husbandry/plant_seed"), MinejagoItems.IRON_SCYTHE.get(), "harvest_with_scythe", AdvancementType.TASK, true, true, false, null, Map.of(
                "harvest_with_scythe", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(BlockTags.CROPS)), ItemPredicate.Builder.item().of(MinejagoItems.IRON_SCYTHE.get()))), "The Ninja Reaper", "Use a Scythe to instantly replant a crop");
    }
}
