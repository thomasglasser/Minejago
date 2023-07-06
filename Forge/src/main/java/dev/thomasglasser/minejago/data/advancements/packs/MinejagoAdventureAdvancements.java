package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.advancements.criterion.BrewedTeaTrigger;
import dev.thomasglasser.minejago.data.advancements.AdvancementUtils;
import dev.thomasglasser.minejago.data.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.data.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Map;
import java.util.function.Consumer;

import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.*;

public class MinejagoAdventureAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {
    private final LanguageProvider enUs;

    public MinejagoAdventureAdvancements(LanguageProvider enUs)
    {
        this.enUs = enUs;
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        AdvancementUtils maker = new AdvancementUtils(saver, existingFileHelper, enUs, CATEGORY);
        
        Advancement killASkulkin = maker.make(new ResourceLocation("adventure/kill_a_mob"), MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED).get(), KILL_A_SKULKIN, FrameType.TASK, true, true, false, null, Map.of(
                "kill_skulkin", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(MinejagoEntityTypeTags.SKULKINS))
        ), "Redead", "Kill a Skulkin Warrior");

        Advancement collectAllSkeletalChestplates = maker.make(killASkulkin, MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.STRENGTH).get(), COLLECT_ALL_SKELETAL_CHESTPLATES, FrameType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(25).build(), Map.of(
                "collect_strength", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.STRENGTH).get()).build()),
                "collect_speed", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED).get()).build()),
                "collect_bow", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.BOW).get()).build()),
                "collect_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.KNIFE).get()).build()),
                "collect_bone", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.BONE).get()).build())
        ), "It's Always You Four Colors", "Collect all of the Skeletal Chestplate variants");

        Advancement getTeapot = maker.make(new ResourceLocation("adventure/root"), MinejagoBlocks.TEAPOT.get(), GET_TEAPOT, FrameType.TASK, true, true, false, null, Map.of(
                "get_teapot", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoItemTags.TEAPOTS).build())
        ), "Something is brewing...", "Acquire a teapot");

        Advancement brewTea = maker.make(getTeapot, PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), MinejagoPotions.REGULAR_TEA.get()), BREW_TEA, FrameType.TASK, true, true, false, null, Map.of(
                "brewed_tea", BrewedTeaTrigger.TriggerInstance.brewedTea()
        ), "Hot Leaf Juice", "Brew tea in a teapot");

        Advancement getJaspot = maker.make(getTeapot, MinejagoBlocks.JASPOT.get(), GET_JASPOT, FrameType.CHALLENGE, true, true, true, AdvancementRewards.Builder.experience(10).build(), Map.of(
                "get_jaspot", InventoryChangeTrigger.TriggerInstance.hasItems(MinejagoBlocks.JASPOT.asItem())
        ), "Whistlepurr", "Acquire the Jaspot");
    }
}
