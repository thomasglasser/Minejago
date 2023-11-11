package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.data.advancements.AdvancementHelper;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Map;
import java.util.function.Consumer;

import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoHusbandryAdvancementKeys.CATEGORY;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoHusbandryAdvancementKeys.HARVEST_WITH_SCYTHE;

public class MinejagoHusbandryAdvancements implements ForgeAdvancementProvider.AdvancementGenerator
{

	private final LanguageProvider enUs;

	public MinejagoHusbandryAdvancements(LanguageProvider enUs)
	{
		this.enUs = enUs;
	}

	@Override
	public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper)
	{
		AdvancementHelper helper = new AdvancementHelper(saver, existingFileHelper, enUs, CATEGORY);

		Advancement harvestWithScythe = helper.make(new ResourceLocation("husbandry/plant_seed"), MinejagoItems.IRON_SCYTHE.get(), HARVEST_WITH_SCYTHE, FrameType.TASK, true, true, false, null, Map.of(
				"harvest_with_scythe", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(BlockTags.CROPS).build()), ItemPredicate.Builder.item().of(MinejagoItems.IRON_SCYTHE.get()))
		), "The Ninja Reaper", "Use a Scythe to instantly replant a crop");
	}
}
