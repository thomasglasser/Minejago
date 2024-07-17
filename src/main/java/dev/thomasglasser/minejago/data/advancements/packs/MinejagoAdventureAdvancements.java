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
import dev.thomasglasser.tommylib.api.data.advancements.ExtendedAdvancementGenerator;
import java.util.Map;
import java.util.Optional;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MinejagoAdventureAdvancements extends ExtendedAdvancementGenerator {
    public MinejagoAdventureAdvancements(LanguageProvider enUs) {
        super(Minejago.MOD_ID, "adventure", enUs);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        AdvancementHolder interactWithMainSix = create(ResourceLocation.withDefaultNamespace("adventure/root"), MinejagoItems.IRON_KATANA.get(), "interact_with_main_six", AdvancementType.GOAL, true, true, false, null, AdvancementRequirements.Strategy.AND, Map.of(
                "interact_wu", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.WU.get()).build()))),
                "interact_nya", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.NYA.get()).build()))),
                "interact_jay", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.JAY.get()).build()))),
                "interact_kai", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.KAI.get()).build()))),
                "interact_cole", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.COLE.get()).build()))),
                "interact_zane", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.ZANE.get()).build())))), "Meet N' Greet", "Interact with Wu, Nya, and the Four Ninja");

        AdvancementHolder killASkulkin = create(ResourceLocation.withDefaultNamespace("adventure/kill_a_mob"), MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED).get(), "kill_a_skulkin", AdvancementType.TASK, true, true, false, null,
                "kill_skulkin", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(MinejagoEntityTypeTags.SKULKINS)), "Redead", "Kill a Skulkin Warrior");

        AdvancementHolder startSkulkinRaid = create(killASkulkin, SkulkinRaid.getLeaderBannerInstance(provider.lookupOrThrow(Registries.BANNER_PATTERN)), "start_skulkin_raid", AdvancementType.GOAL, true, true, false, null,
                "start_skulkin_raid", SkulkinRaidTrigger.TriggerInstance.raidStarted(), "Ninja, GO!", "The Skulkin are coming!");

        AdvancementHolder winSkulkinRaid = create(startSkulkinRaid, Items.FILLED_MAP, "win_skulkin_raid", AdvancementType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(10).build(),
                "win_skulkin_raid", SkulkinRaidTrigger.TriggerInstance.raidWon(), "Ninja, Gone!", "Defeat a Skulkin Raid");

        AdvancementHolder killSamukai = create(startSkulkinRaid, MinejagoArmors.SAMUKAIS_CHESTPLATE.get(), "kill_samukai", AdvancementType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(10).build(),
                "kill_samukai", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.SAMUKAI.get())), "Knives Out", "Defeat Samukai");

        AdvancementHolder collectAllSkeletalChestplates = create(killASkulkin, MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.STRENGTH).get(), "collect_all_skeletal_chestplates", AdvancementType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(25).build(), AdvancementRequirements.Strategy.AND, Map.of(
                "collect_strength", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.STRENGTH).get()).build()),
                "collect_speed", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED).get()).build()),
                "collect_bow", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.BOW).get()).build()),
                "collect_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.KNIFE).get()).build())), "It's Always You Four Colors", "Collect all of the Skeletal Chestplate variants");

        AdvancementHolder getTeapot = create(ResourceLocation.withDefaultNamespace("adventure/root"), MinejagoBlocks.TEAPOT.get(), "get_teapot", AdvancementType.TASK, true, true, false, null,
                "get_teapot", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(MinejagoItemTags.TEAPOTS).build()), "Something is brewing...", "Acquire a teapot");

        AdvancementHolder brewTea = create(getTeapot, PotionContents.createItemStack(MinejagoItems.FILLED_TEACUP.get(), MinejagoPotions.OAK_TEA.asReferenceFrom(provider)), "brew_tea", AdvancementType.TASK, true, true, false, null,
                "brewed_tea", BrewedTeaTrigger.TriggerInstance.brewedTea(), "Hot Leaf Juice", "Brew tea in a teapot");

        AdvancementHolder getJaspot = create(getTeapot, MinejagoBlocks.JASPOT.get(), "get_jaspot", AdvancementType.CHALLENGE, true, true, true, AdvancementRewards.Builder.experience(10).build(),
                "get_jaspot", InventoryChangeTrigger.TriggerInstance.hasItems(MinejagoBlocks.JASPOT.get()), "Whistlepurr", "Acquire the Jaspot");
    }
}
