package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.criterion.BrewedTeaTrigger;
import dev.thomasglasser.minejago.advancements.criterion.SkulkinRaidTrigger;
import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaid;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.data.advancements.AdvancementHelper;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.BREW_TEA;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.CATEGORY;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.COLLECT_ALL_SKELETAL_CHESTPLATES;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.GET_JASPOT;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.GET_TEAPOT;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.INTERACT_WITH_MAIN_SIX;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.KILL_A_SKULKIN;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.KILL_SAMUKAI;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.START_SKULKIN_RAID;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys.WIN_SKULKIN_RAID;

public class MinejagoAdventureAdvancements implements AdvancementProvider.AdvancementGenerator {
    private final LanguageProvider enUs;

    public MinejagoAdventureAdvancements(LanguageProvider enUs)
    {
        this.enUs = enUs;
    }
    
    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper)
    {
        AdvancementHelper helper = new AdvancementHelper(consumer, Minejago.MOD_ID, existingFileHelper, enUs, CATEGORY);

        AdvancementHolder interactWithMainSix = helper.make(new ResourceLocation("adventure/root"), MinejagoItems.IRON_KATANA.get(), INTERACT_WITH_MAIN_SIX, AdvancementType.GOAL, true, true, false, null, Map.of(
                "interact_wu", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.WU.get()).build()))),
                "interact_nya", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.NYA.get()).build()))),
                "interact_jay", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.JAY.get()).build()))),
                "interact_kai", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.KAI.get()).build()))),
                "interact_cole", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.COLE.get()).build()))),
                "interact_zane", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.ZANE.get()).build())))
        ), "Meet N' Greet", "Interact with Wu, Nya, and the Four Ninja");

        AdvancementHolder killASkulkin = helper.make(new ResourceLocation("adventure/kill_a_mob"), MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED).get(), KILL_A_SKULKIN, AdvancementType.TASK, true, true, false, null, Map.of(
                "kill_skulkin", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(MinejagoEntityTypeTags.SKULKINS))
        ), "Redead", "Kill a Skulkin Warrior");

        AdvancementHolder startSkulkinRaid = helper.make(killASkulkin, SkulkinRaid.getLeaderBannerInstance(), START_SKULKIN_RAID, AdvancementType.GOAL, true, true, false, null, Map.of(
                "start_skulkin_raid", SkulkinRaidTrigger.TriggerInstance.raidStarted()
        ), "Ninja, GO!", "The Skulkin are coming!");

        AdvancementHolder winSkulkinRaid = helper.make(startSkulkinRaid, Items.FILLED_MAP, WIN_SKULKIN_RAID, AdvancementType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(10).build(), Map.of(
                "win_skulkin_raid", SkulkinRaidTrigger.TriggerInstance.raidWon()
        ), "Ninja, Gone!", "Defeat a Skulkin Raid");

        AdvancementHolder killSamukai = helper.make(startSkulkinRaid, MinejagoArmors.SAMUKAIS_CHESTPLATE.get(), KILL_SAMUKAI, AdvancementType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(10).build(), Map.of(
                "kill_samukai", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.SAMUKAI.get()))
        ), "Knives Out", "Defeat Samukai");

        AdvancementHolder collectAllSkeletalChestplates = helper.make(killASkulkin, MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.STRENGTH).get(), COLLECT_ALL_SKELETAL_CHESTPLATES, AdvancementType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(25).build(), Map.of(
                "collect_strength", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.STRENGTH).get()).build()),
                "collect_speed", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED).get()).build()),
                "collect_bow", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.BOW).get()).build()),
                "collect_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.KNIFE).get()).build())
        ), "It's Always You Four Colors", "Collect all of the Skeletal Chestplate variants");

        AdvancementHolder getTeapot = helper.make(new ResourceLocation("adventure/root"), MinejagoBlocks.TEAPOT.get(), GET_TEAPOT, AdvancementType.TASK, true, true, false, null, Map.of(
                "get_teapot", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoItemTags.TEAPOTS).build())
        ), "Something is brewing...", "Acquire a teapot");

        AdvancementHolder brewTea = helper.make(getTeapot, PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), MinejagoPotions.OAK_TEA.get()), BREW_TEA, AdvancementType.TASK, true, true, false, null, Map.of(
                "brewed_tea", BrewedTeaTrigger.TriggerInstance.brewedTea()
        ), "Hot Leaf Juice", "Brew tea in a teapot");

        AdvancementHolder getJaspot = helper.make(getTeapot, MinejagoBlocks.JASPOT.get(), GET_JASPOT, AdvancementType.CHALLENGE, true, true, true, AdvancementRewards.Builder.experience(10).build(), Map.of(
                "get_jaspot", InventoryChangeTrigger.TriggerInstance.hasItems(MinejagoBlocks.JASPOT.get())
        ), "Whistlepurr", "Acquire the Jaspot");
    }
}
