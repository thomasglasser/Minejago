package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.advancements.criterion.DoSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GetPowerTrigger;
import dev.thomasglasser.minejago.data.advancements.AdvancementHelper;
import dev.thomasglasser.minejago.data.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.PowerUtils;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.LanguageProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.function.Consumer;

import static dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys.*;

public class MinejagoStoryAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    private final LanguageProvider enUs;

    public MinejagoStoryAdvancements(LanguageProvider enUs)
    {
        this.enUs = enUs;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        AdvancementHelper helper = new AdvancementHelper(saver, existingFileHelper, enUs, CATEGORY);

        Advancement root = helper.root(MinejagoItems.SCYTHE_OF_QUAKES.get(), ROOT, new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false, null, Map.of(
                "get_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE)
        ), "Minejago", "Long before time had a name...");

        Advancement getBlackGi = helper.make(root, MinejagoArmors.BLACK_GI_SET.HEAD.get(), GET_BLACK_GI, FrameType.TASK, true, true, false, null, Map.of(
                "get_black_gi_set", InventoryChangeTrigger.TriggerInstance.hasItems(MinejagoArmors.BLACK_GI_SET.getAllAsItems().toArray(new ItemLike[] {}))
        ), "Ninja in Training", "Receive the Black Gi");

        Advancement doSpinjitzu = helper.make(getBlackGi, PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), MinejagoPotions.OAK_TEA.get()), DO_SPINJITZU, FrameType.TASK, true, true, false, null, Map.of(
                "do_spinjitzu", DoSpinjitzuTrigger.TriggerInstance.didSpinjitzu()
        ), "Twistitzu? Tornadzu?", "Do spinjitzu for the first time");

        CompoundTag fwTag = new CompoundTag();
        fwTag.putBoolean("IsFourWeaponsMap", true);

        Advancement getFourWeaponsMap = helper.make(getBlackGi, Items.FILLED_MAP, GET_FOUR_WEAPONS_MAP, FrameType.TASK, true, true, false, null, Map.of(
                "get_four_weapons_map", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Items.FILLED_MAP).hasNbt(fwTag).build())
        ), "The Journey Begins", "Obtain a Four Weapons Map");

        Advancement getPower = helper.make(getFourWeaponsMap, PowerUtils.setPower(MinejagoArmors.TRAINING_GI_SET.HEAD.get().getDefaultInstance(), MinejagoPowers.FIRE), GET_POWER, FrameType.TASK, true, true, false, null, Map.of(
                "get_power", GetPowerTrigger.TriggerInstance.gotAnyPower()
        ), "I've Got the Power!", "Discover your elemental power");

        Advancement enterGoldenWeaponsStructure = helper.make(getPower, MinejagoItems.SCYTHE_OF_QUAKES.get(), ENTER_GOLDEN_WEAPONS_STRUCTURE, FrameType.TASK, true, true, false, null, Map.of(
                "enter_cave_of_despair", PlayerTrigger.TriggerInstance.located(LocationPredicate.inStructure(MinejagoStructures.CAVE_OF_DESPAIR))
                // TODO: Add other structures
        ), "The Weapon is Near", "Enter a structure containing a Golden Weapon");

        Advancement tameDragon = helper.make(enterGoldenWeaponsStructure, Items.SADDLE, TAME_DRAGON, FrameType.CHALLENGE, true, true, false, AdvancementRewards.Builder.experience(15).build(), Map.of(
                "tame_dragon", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(MinejagoEntityTypeTags.DRAGONS).build())
        ), "Out of this World", "Tame a dragon");
    }
}
