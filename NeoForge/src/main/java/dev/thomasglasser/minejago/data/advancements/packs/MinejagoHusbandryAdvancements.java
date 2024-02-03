package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.data.advancements.AdvancementHelper;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Map;
import java.util.function.Consumer;

import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoHusbandryAdvancementKeys.CATEGORY;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoHusbandryAdvancementKeys.HARVEST_WITH_SCYTHE;

public class MinejagoHusbandryAdvancements implements AdvancementProvider.AdvancementGenerator
{

	private final LanguageProvider enUs;

	public MinejagoHusbandryAdvancements(LanguageProvider enUs)
	{
		this.enUs = enUs;
	}

	@Override
	public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper)
	{
		AdvancementHelper helper = new AdvancementHelper(saver, Minejago.MOD_ID, existingFileHelper, enUs, CATEGORY);

		AdvancementHolder harvestWithScythe = helper.make(new ResourceLocation("husbandry/plant_seed"), MinejagoItems.IRON_SCYTHE.get(), HARVEST_WITH_SCYTHE, AdvancementType.TASK, true, true, false, null, Map.of(
				"harvest_with_scythe", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(BlockTags.CROPS)), ItemPredicate.Builder.item().of(MinejagoItems.IRON_SCYTHE.get()))
		), "The Ninja Reaper", "Use a Scythe to instantly replant a crop");
	}
}
