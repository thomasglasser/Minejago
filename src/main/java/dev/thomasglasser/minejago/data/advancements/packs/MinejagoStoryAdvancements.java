package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.criterion.DidSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GotPowerTrigger;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import dev.thomasglasser.tommylib.api.data.advancements.ExtendedAdvancementGenerator;
import java.util.Map;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MinejagoStoryAdvancements extends ExtendedAdvancementGenerator {
    public MinejagoStoryAdvancements(LanguageProvider enUs) {
        super(Minejago.MOD_ID, "story", enUs);
    }

    @Override
    public void generate(HolderLookup.Provider registries) {
        AdvancementHolder root = root(MinejagoItems.SCYTHE_OF_QUAKES.get(), "root", ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"), AdvancementType.TASK, false, false, false, null,
                "get_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE), "Minejago", "Long before time had a name...");

        AdvancementHolder getBlackGi = create(root, MinejagoArmors.BLACK_GI_SET.HEAD.get(), "get_black_gi", AdvancementType.TASK, true, true, false, null,
                "get_black_gi_set", InventoryChangeTrigger.TriggerInstance.hasItems(MinejagoArmors.BLACK_GI_SET.getAllAsItems().toArray(new ItemLike[] {})), "Ninja in Training", "Receive the Black Gi");

        AdvancementHolder doSpinjitzu = create(getBlackGi, PotionContents.createItemStack(MinejagoItems.FILLED_TEACUP.get(), MinejagoPotions.OAK_TEA.asReferenceFrom(registries)), "do_spinjitzu", AdvancementType.TASK, true, true, false, null,
                "do_spinjitzu", DidSpinjitzuTrigger.TriggerInstance.didSpinjitzu(), "Twistitzu? Tornadzu?", "Do spinjitzu for the first time");

        AdvancementHolder getFourWeaponsMap = create(getBlackGi, Items.FILLED_MAP, "get_four_weapons_map", AdvancementType.TASK, true, true, false, null,
                "get_four_weapons_map", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Items.FILLED_MAP).hasComponents(DataComponentPredicate.builder().expect(MinejagoDataComponents.GOLDEN_WEAPONS_MAP.get(), Unit.INSTANCE).build()).build()), "The Journey Begins", "Obtain a Four Weapons Map");

        ItemStack fireHead = MinejagoArmors.TRAINING_GI_SET.HEAD.get().getDefaultInstance();
        fireHead.set(MinejagoDataComponents.POWER.get(), MinejagoPowers.FIRE.location());
        AdvancementHolder getPower = create(getFourWeaponsMap, fireHead, "get_power", AdvancementType.TASK, true, true, false, null,
                "get_power", GotPowerTrigger.TriggerInstance.gotPower(), "I've Got the Power!", "Discover your elemental power");

        Map<String, Criterion<?>> goldenWeaponsStructures = Map.of(
                "enter_cave_of_despair", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(registries.lookupOrThrow(Registries.STRUCTURE).getOrThrow(MinejagoStructures.CAVE_OF_DESPAIR)))
        // TODO: Add other structures
        );

        AdvancementHolder enterGoldenWeaponsStructure = create(getPower, MinejagoItems.SCYTHE_OF_QUAKES.get(), "enter_golden_weapons_structure", AdvancementType.TASK, true, true, false, null, AdvancementRequirements.Strategy.OR, goldenWeaponsStructures, "The Weapon is Near", "Enter a structure containing a Golden Weapon");

        AdvancementHolder enterAllGoldenWeaponsStructures = create(enterGoldenWeaponsStructure, MinejagoItems.SCYTHE_OF_QUAKES.get()/*TODO: Replace with sword of fire*/, "enter_all_golden_weapons_structures", AdvancementType.GOAL, true, true, false, AdvancementRewards.Builder.experience(50).build(), AdvancementRequirements.Strategy.AND, goldenWeaponsStructures, "The Homes of Creation", "Find all structures containing Golden Weapons");

        AdvancementHolder tameDragon = create(enterGoldenWeaponsStructure, Items.SADDLE, "tame_dragon", AdvancementType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(15).build(),
                "tame_dragon", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(MinejagoEntityTypeTags.DRAGONS)), "Out of this World", "Tame a dragon");
    }
}
