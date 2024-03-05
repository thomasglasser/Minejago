package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.criterion.DidSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GotPowerTrigger;
import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.PowerUtils;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import dev.thomasglasser.tommylib.api.data.advancements.AdvancementHelper;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.function.Consumer;

import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.CATEGORY;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.DO_SPINJITZU;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.ENTER_GOLDEN_WEAPONS_STRUCTURE;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.GET_BLACK_GI;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.GET_FOUR_WEAPONS_MAP;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.GET_POWER;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.ROOT;
import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.TAME_DRAGON;

public class MinejagoStoryAdvancements implements AdvancementProvider.AdvancementGenerator
{

    private final LanguageProvider enUs;

    public MinejagoStoryAdvancements(LanguageProvider enUs)
    {
        this.enUs = enUs;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        AdvancementHelper helper = new AdvancementHelper(saver, Minejago.MOD_ID, existingFileHelper, enUs, CATEGORY);

        AdvancementHolder root = helper.root(MinejagoItems.SCYTHE_OF_QUAKES.get(), ROOT, new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), AdvancementType.TASK, false, false, false, null, Map.of(
                "get_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE)
        ), "Minejago", "Long before time had a name...");

        AdvancementHolder getBlackGi = helper.make(root, MinejagoArmors.BLACK_GI_SET.HEAD.get(), GET_BLACK_GI, AdvancementType.TASK, true, true, false, null, Map.of(
                "get_black_gi_set", InventoryChangeTrigger.TriggerInstance.hasItems(MinejagoArmors.BLACK_GI_SET.getAllAsItems().toArray(new ItemLike[] {}))
        ), "Ninja in Training", "Receive the Black Gi");

        AdvancementHolder doSpinjitzu = helper.make(getBlackGi, PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), MinejagoPotions.OAK_TEA.get()), DO_SPINJITZU, AdvancementType.TASK, true, true, false, null, Map.of(
                "do_spinjitzu", DidSpinjitzuTrigger.TriggerInstance.didSpinjitzu()
        ), "Twistitzu? Tornadzu?", "Do spinjitzu for the first time");

        CompoundTag fwTag = new CompoundTag();
        fwTag.putBoolean("IsFourWeaponsMap", true);

        AdvancementHolder getFourWeaponsMap = helper.make(getBlackGi, Items.FILLED_MAP, GET_FOUR_WEAPONS_MAP, AdvancementType.TASK, true, true, false, null, Map.of(
                "get_four_weapons_map", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Items.FILLED_MAP).hasNbt(fwTag).build())
        ), "The Journey Begins", "Obtain a Four Weapons Map");

        AdvancementHolder getPower = helper.make(getFourWeaponsMap, PowerUtils.setPower(MinejagoArmors.TRAINING_GI_SET.HEAD.get().getDefaultInstance(), MinejagoPowers.FIRE), GET_POWER, AdvancementType.TASK, true, true, false, null, Map.of(
                "get_power", GotPowerTrigger.TriggerInstance.gotPower()
        ), "I've Got the Power!", "Discover your elemental power");

        AdvancementHolder enterGoldenWeaponsStructure = helper.make(getPower, MinejagoItems.SCYTHE_OF_QUAKES.get(), ENTER_GOLDEN_WEAPONS_STRUCTURE, AdvancementType.TASK, true, true, false, null, Map.of(
                "enter_cave_of_despair", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(MinejagoStructures.CAVE_OF_DESPAIR))
                // TODO: Add other structures
        ), "The Weapon is Near", "Enter a structure containing a Golden Weapon");

        AdvancementHolder tameDragon = helper.make(enterGoldenWeaponsStructure, Items.SADDLE, TAME_DRAGON, AdvancementType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(15).build(), Map.of(
                "tame_dragon", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(MinejagoEntityTypeTags.DRAGONS))
        ), "Out of this World", "Tame a dragon");
    }
}
