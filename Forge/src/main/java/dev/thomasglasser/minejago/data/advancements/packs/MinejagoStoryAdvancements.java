package dev.thomasglasser.minejago.data.advancements.packs;

import dev.thomasglasser.minejago.advancements.criterion.DoSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GetPowerTrigger;
import dev.thomasglasser.minejago.data.advancements.AdvancementUtils;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.PowerUtils;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.PlayerInteractTrigger;
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
        AdvancementUtils maker = new AdvancementUtils(saver, existingFileHelper, enUs, CATEGORY);

        Advancement root = maker.root(MinejagoItems.SCYTHE_OF_QUAKES.get(), ROOT, new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false, null, Map.of(
                "get_crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE)
        ), "Minejago", "Long before time had a name...");

        Advancement getBlackGi = maker.make(root, MinejagoArmors.BLACK_GI_SET.HEAD.get(), GET_BLACK_GI, FrameType.TASK, true, true, false, null, Map.of(
                "get_black_gi_set", InventoryChangeTrigger.TriggerInstance.hasItems(MinejagoArmors.BLACK_GI_SET.getAllAsItems().toArray(new ItemLike[] {}))
        ), "Ninja in Training", "Receive the Black Gi");

        Advancement doSpinjitzu = maker.make(getBlackGi, PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), MinejagoPotions.REGULAR_TEA.get()), DO_SPINJITZU, FrameType.TASK, true, true, false, null, Map.of(
                "do_spinjitzu", DoSpinjitzuTrigger.TriggerInstance.didSpinjitzu()
        ), "Twistitzu? Tornadzu?", "Do spinjitzu for the first time");

        CompoundTag fwTag = new CompoundTag();
        fwTag.putBoolean("IsFourWeaponsMap", true);

        Advancement getFourWeaponsMap = maker.make(getBlackGi, Items.FILLED_MAP, GET_FOUR_WEAPONS_MAP, FrameType.TASK, true, true, false, null, Map.of(
                "get_four_weapons_map", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Items.FILLED_MAP).hasNbt(fwTag).build())
        ), "The Journey Begins", "Obtain a Four Weapons Map");

        Advancement getPower = maker.make(getFourWeaponsMap, PowerUtils.setPower(MinejagoArmors.TRAINING_GI_SET.HEAD.get().getDefaultInstance(), MinejagoPowers.FIRE), GET_POWER, FrameType.TASK, true, true, false, null, Map.of(
                "get_power", GetPowerTrigger.TriggerInstance.gotAnyPower()
        ), "I've Got the Power!", "Discover your elemental power");

        Advancement interactWithMainSix = maker.make(root, MinejagoItems.IRON_KATANA.get(), INTERACT_WITH_MAIN_SIX, FrameType.GOAL, true, true, false, null,
                Map.of(
                        "interact_wu", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.WU.get()).build())),
                        "interact_nya", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.NYA.get()).build())),
                        "interact_jay", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.JAY.get()).build())),
                        "interact_kai", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.KAI.get()).build())),
                        "interact_cole", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.COLE.get()).build())),
                        "interact_zane", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item(), EntityPredicate.wrap(EntityPredicate.Builder.entity().of(MinejagoEntityTypes.ZANE.get()).build()))
                ), "Meet N' Greet", "Interact with Wu, Nya, and the Four Ninja");
    }
}
